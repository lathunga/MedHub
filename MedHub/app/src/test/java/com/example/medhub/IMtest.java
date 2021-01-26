package com.example.medhub;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IMtest {
    @Mock
    private IMinterface view;
    @Mock
    private InstantMessage service;
    private IMpresenter presenter;
    @Before
    public void setUp() throws Exception{
        service.myName = "Evan";
        presenter = new IMpresenter(view, service);
    }
    @Test
    public void TestMessages(){
        when(service.isThereText()).thenReturn(false);
    }
    @Test
    public void TestMessages2(){
        service.AddMessage("Me: Fake Message");
        when(service.isThereText()).thenReturn(true);
    }
}

