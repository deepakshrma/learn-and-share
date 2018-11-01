package com.dbs.replsdk.ui.events;

import com.dbs.replsdk.model.Request;
import com.dbs.ui.multiadapter.GroupEvent;

public abstract class KaiEvent implements GroupEvent {

    public abstract Request createRequest();
}
