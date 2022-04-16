package com.test.qatools.pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Pages {

    @Autowired
    private QAToolHomePage qaToolHomePage;

    public QAToolHomePage qaToolHomePage() {
        return this.qaToolHomePage;
    }

}
