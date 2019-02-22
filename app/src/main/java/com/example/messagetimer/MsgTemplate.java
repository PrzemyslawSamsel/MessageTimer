package com.example.messagetimer;

import java.util.ArrayList;
import java.util.List;

public class MsgTemplate
{
    private String content;

    public List<String> templates = new ArrayList<String>();

    public MsgTemplate()
    {
        templates.add("");
        templates.add("Hey, wlasnie dojechalismy");
        templates.add("Zostalo nam okolo pol godziny drogi");
    }

    public String getContent()
    {
        return this.content;
    }

    public String toString()
    {
        return this.content;
    }

    public void addTemplate(String text)
    {
        templates.add(text);
    }

    public void deleteTemplate(long id)
    {

    }

}
