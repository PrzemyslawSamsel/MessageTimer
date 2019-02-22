package com.example.messagetimer;

public class MsgTemplate
{
    private String content;

    public static final MsgTemplate[] templates =
            {
                    new MsgTemplate("Hey, wlasnie dojechalismy"),
                    new MsgTemplate("Zostalo nam okolo pol godziny drogi")
            };

    private MsgTemplate(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return this.content;
    }

    public String toString()
    {
        return this.content;
    }

}
