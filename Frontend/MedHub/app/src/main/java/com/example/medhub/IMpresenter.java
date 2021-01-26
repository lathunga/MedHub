package com.example.medhub;

public class IMpresenter {
    private IMinterface view;
    private InstantMessage service;
    public IMpresenter(IMinterface view, InstantMessage service)
    {
        this.view = view;
        this.service = service;
    }
    public void onSubmitClicked()
    {
        service.webSocketClient.send(service.id + service.key+service.myName+service.key+"Time"+service.key);
    }
}
