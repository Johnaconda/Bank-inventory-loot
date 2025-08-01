// Requires compileOnly dependency on net.runelite:runelite-client to access net.runelite.rs.api.RSWidget
package com.johnaconda.autobank;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.runelite.api.Client;
import net.runelite.api.ChatMessageType;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.events.WidgetLoaded;

import net.runelite.client.callback.ClientThread;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import net.runelite.rs.api.RSWidget;

@PluginDescriptor(
        name        = "Auto Bank Deposit",
        description = "Deposits your inventory once when the bank UI opens using RSWidget.interact(0)",
        tags        = {"bank","inventory","auto"}
)
@Singleton
public class AutoBankPlugin extends Plugin
{
    @Inject private Client client;
    @Inject private ClientThread clientThread;

    @Override
    protected void startUp() throws Exception
    {
        clientThread.invokeLater(() ->
                client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "[AutoBank] Plugin started", null)
        );
    }

    @Override
    protected void shutDown() throws Exception
    {
        clientThread.invokeLater(() ->
                client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "[AutoBank] Plugin stopped", null)
        );
    }

    @Subscribe
    public void onWidgetLoaded(WidgetLoaded event)
    {
        // Only trigger when the bank interface finishes loading
        if (event.getGroupId() != WidgetInfo.BANK_ITEM_CONTAINER.getGroupId())
        {
            return;
        }

        clientThread.invokeLater(() ->
        {
            // Find the Deposit Inventory button
            Widget depositBtn = client.getWidget(WidgetInfo.BANK_DEPOSIT_INVENTORY);
            if (depositBtn == null || depositBtn.isHidden())
            {
                return;
            }

            // Use RuneLite's injected RSWidget.interact(0) to click it
            ((RSWidget) depositBtn).interact(0);

            client.addChatMessage(
                    ChatMessageType.GAMEMESSAGE,
                    "",
                    "[AutoBank] depositBtn.interact(0) called",
                    null
            );
        });
    }
}
