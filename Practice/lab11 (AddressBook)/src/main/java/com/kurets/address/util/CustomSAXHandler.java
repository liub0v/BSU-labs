package com.kurets.address.util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.time.LocalDate;
import java.time.Period;

public class CustomSAXHandler extends DefaultHandler {


    private int personsNumber;
    private int averageOld = 0;
    private boolean tagBirthday = false;


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (qName.equals("Persons")) {
            personsNumber = 0;
        }
        if (qName.equals("Person")) {
            personsNumber++;
        }
        if (qName.equals("birthday")) {
            tagBirthday = true;

        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        if (tagBirthday) {
            LocalDate birthday = LocalDate.parse(new String(ch, start, length));
            averageOld += Period.between(birthday, LocalDate.now()).getYears();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (qName.equals("birthday")) {
            tagBirthday = false;
        }
        if (qName.equals("Persons")) {
            averageOld /= personsNumber;
        }
    }

    public int getAverageOld() {
        return averageOld;
    }

    public int getPersonsNumber() {
        return personsNumber;
    }
}
