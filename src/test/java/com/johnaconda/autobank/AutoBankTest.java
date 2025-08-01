package com.johnaconda.autobank;

import com.johnaconda.autobank.AutoBankPlugin;
import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class AutoBankTest
{
    public static void main(String[] args) throws Exception
    {
        ExternalPluginManager.loadBuiltin(AutoBankPlugin.class);
        RuneLite.main(args);
    }
}