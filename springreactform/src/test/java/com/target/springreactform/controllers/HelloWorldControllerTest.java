package com.target.springreactform.controllers;

import com.target.ems.hello.HelloWorldController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HelloWorldControllerTest {

    HelloWorldController systemUnderTest;

    @BeforeEach
    void setUp() {
        systemUnderTest = new HelloWorldController();
    }

    @Test
    void shouldSayHello() {
        assertEquals("Hello World!", systemUnderTest.hello(), "Incorrect greeting.");
    }
}