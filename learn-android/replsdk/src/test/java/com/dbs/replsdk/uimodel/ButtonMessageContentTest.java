package com.dbs.replsdk.uimodel;

import com.dbs.replsdk.ui.items.ButtonItem;
import com.dbs.replsdk.ui.items.DisplayOption;
import com.xwray.groupie.Group;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class ButtonMessageContentTest {

    private Button button;

    @Before
    public void setup() {
        button = new Button();
        button.setLabel("Test label");
        button.setPayload("Test payload");
        button.setType(Button.ButtonType.POSTBACK);
    }

    @Test
    public void createItemTest() {
        ButtonMessageContent content = new ButtonMessageContent();
        content.setPayload(button);
        Group item = content.createGroup(null, null, new DisplayOption());
        assertThat(item, instanceOf(ButtonItem.class));
    }
}
