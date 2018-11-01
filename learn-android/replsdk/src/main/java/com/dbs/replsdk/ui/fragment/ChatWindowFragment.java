package com.dbs.replsdk.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.dbs.replsdk.R;
import com.dbs.replsdk.arch.ChatBotViewModel;
import com.dbs.replsdk.arch.Injection;
import com.dbs.replsdk.arch.ViewModelFactory;
import com.dbs.replsdk.model.QuickReply;
import com.dbs.replsdk.ui.components.ComposeMessageView;
import com.dbs.replsdk.ui.components.QuickReplyView;
import com.dbs.replsdk.ui.events.ButtonEventHandler;
import com.dbs.replsdk.ui.events.ErrorEvent;
import com.dbs.replsdk.ui.events.HyperLinkOpener;
import com.dbs.replsdk.ui.events.ReplyingEvent;
import com.dbs.replsdk.ui.events.SubjectEvent;
import com.dbs.replsdk.ui.events.TextEvent;
import com.dbs.replsdk.ui.events.TextEventHandler;
import com.dbs.replsdk.ui.items.ItemOffsetDecoration;
import com.dbs.replsdk.ui.items.ReplyingItem;
import com.dbs.replsdk.ui.items.UiChatMessageFactory;
import com.dbs.replsdk.uimodel.UiChatMessage;
import com.dbs.ui.multiadapter.GroupItemFactory;
import com.dbs.ui.multiadapter.MultipleTypeAdapter;
import com.dbs.ui.multiadapter.StrategyHandlers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deepak on 5/10/18.
 */

public class ChatWindowFragment extends Fragment implements HyperLinkOpener, ComposeMessageView.ComposeMessageViewHost {


    /**
     * Chatbot ViewModel
     */
    private ChatBotViewModel mViewModel;
    /**
     * Main chat recycler view
     */
    private RecyclerView chatRecyclerView;
    /**
     * Quick replies recycler view
     */
    private QuickReplyView quickRepliesRecyclerView;
    /**
     * layout manager for chat recycler view
     */
    private LinearLayoutManager chatViewLayoutManager;
    /**
     * Main chat adapter
     */
    private MultipleTypeAdapter chatAdapter;
    /**
     * Message input view
     */
    private ComposeMessageView chatToolbar;

    /**
     * handler for TEXT message.
     */
    private TextEventHandler sharedTextHandler;
    /**
     * Main thread handler
     */
    private Handler mainHandler;


    private UiChatMessageFactory uiChatMessageFactory = new UiChatMessageFactory();

    private int mScrollToDismissThreshold = 0;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat_window_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewModel();
        initChatToolbar(view);
        initRecyclerView(view);
        ViewConfiguration.get(getActivity()).getScaledTouchSlop();
        ImageButton close = view.findViewById(R.id.close);
        close.setOnClickListener(v -> requireActivity().finish());
    }

    private void initViewModel() {
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this.requireContext());
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ChatBotViewModel.class);
        mViewModel.getChatsLiveData().observe(this.getViewLifecycleOwner(), this::updateChats);
        mViewModel.getQuickRepliesLiveData().observe(this.getViewLifecycleOwner(), this::updateQuickReplies);
        mViewModel.getEventLiveData().observe(this.getViewLifecycleOwner(), this::handleEvent);
        mViewModel.initStream();
        sharedTextHandler = new TextEventHandler(mViewModel);
    }


    private void initChatToolbar(View view) {
        chatToolbar = view.findViewById(R.id.chatToolbar);
        chatToolbar.bind(this);
    }

    @Override
    public void openLink(String link) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder().setToolbarColor(ContextCompat.getColor(this.requireContext(), R.color.my_bubble));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this.requireContext(), Uri.parse(link));
    }

    private void initRecyclerView(View view) {
        // normal stuff for RecyclerView
        chatRecyclerView = view.findViewById(R.id.chatRecyclerView);
        chatViewLayoutManager = new LinearLayoutManager(requireContext());
        chatRecyclerView.setLayoutManager(chatViewLayoutManager);
        chatRecyclerView.addItemDecoration(new ItemOffsetDecoration(requireContext()));

        chatRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private int mCumulativeScrollDelta;
            private boolean mScrollToDismissHandled;
            private int mScrollState = RecyclerView.SCROLL_STATE_IDLE;

            @Override
            public void onScrollStateChanged(final RecyclerView view, final int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // Reset scroll states.
                    mCumulativeScrollDelta = 0;
                    mScrollToDismissHandled = false;
                } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    chatRecyclerView.getItemAnimator().endAnimations();
                }
                mScrollState = newState;
            }

            @Override
            public void onScrolled(final RecyclerView view, final int dx, final int dy) {
                if (mScrollState == RecyclerView.SCROLL_STATE_DRAGGING &&
                        !mScrollToDismissHandled) {
                    mCumulativeScrollDelta += dy;
                    // Dismiss the keyboard only when the user scroll up (into the past).
                    if (mCumulativeScrollDelta < -mScrollToDismissThreshold) {
                        chatToolbar.hideAllComposeInputs(false /* animate */);
                        mScrollToDismissHandled = true;
                    }
                }

            }
        });
        // Groupie factories
        List<GroupItemFactory> factories = new ArrayList<>();
        factories.add(uiChatMessageFactory);
        //event handlers
        StrategyHandlers handlers = new StrategyHandlers();
        handlers.registerHandler(sharedTextHandler);
        handlers.registerHandler(new ButtonEventHandler(mViewModel).withLinkOpener(this));
        //set adapter
        chatAdapter = new MultipleTypeAdapter(factories, handlers);
        chatAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                chatViewLayoutManager.scrollToPosition(chatAdapter.getItemCount() - 1);
            }
        });
        chatRecyclerView.setAdapter(chatAdapter);

        // Quick replies View
        quickRepliesRecyclerView = view.findViewById(R.id.quickRepliesRecyclerView);
        quickRepliesRecyclerView.setOnQuickReplyListener(sharedTextHandler::handle);
    }

    private void updateChats(List<UiChatMessage> chats) {
        if (chats.isEmpty()) {
            mViewModel.startSession();
        } else {
            chatAdapter.updateListModel(chats);
        }
    }

    private void updateQuickReplies(List<QuickReply> quickReplies) {

        if (quickReplies != null && quickReplies.size() > 0) {
            quickRepliesRecyclerView.setVisibility(View.VISIBLE);
            quickRepliesRecyclerView.updateReplies(quickReplies);
            quickRepliesRecyclerView.postDelayed(() -> quickRepliesRecyclerView.smoothScrollToPosition(0), 100L);

        } else {
            quickRepliesRecyclerView.setVisibility(View.GONE);
        }
    }

    private void handleEvent(SubjectEvent event) {
        if (event instanceof ErrorEvent) {
            Toast.makeText(requireContext(), ((ErrorEvent) event).getErrorMessage(), Toast.LENGTH_LONG).show();
        }
        if (event instanceof ReplyingEvent) {
            mainHandler.postDelayed(() -> chatAdapter.add(new ReplyingItem()), 200);
        }

    }

    @Override
    public void sendMessage(String message) {
        sharedTextHandler.handle(new TextEvent(message));
    }

    @Override
    public void onComposeEditTextFocused() {
    }
}