package mrunknown404.chatstamps;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkConstants;

@Mod(ChatStamps.MOD_ID)
public class ChatStamps {
	public static final String MOD_ID = "chatstamps";
	
	public ChatStamps() {
		ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class,
				() -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true));
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	private void clientSetup(@SuppressWarnings("unused") FMLClientSetupEvent e) {
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigManager.SPEC);
	}
	
	@SubscribeEvent
	public void onChat(ClientChatReceivedEvent e) {
		if (e.getType() == ChatType.CHAT) {
			ConfigManager c = ConfigManager.INSTANCE;
			
			try {
				String s = new SimpleDateFormat(c.dateFormat.get()).format(new Date());
				
				MutableComponent txt = string(c.startChar.get(), c.startCharColor.get()).append(string(s, c.timeColor.get())).append(string(c.endChar.get(), c.endCharColor.get()))
						.append(new TextComponent(" ").withStyle(Style.EMPTY)).append(e.getMessage());
				
				e.setMessage(txt);
			} catch (@SuppressWarnings("unused") Exception e0) {
				System.out.println("Invalid date format!");
				return;
			}
		}
	}
	
	private static MutableComponent string(String str, ChatFormatting format) {
		return new TextComponent(str).withStyle(Style.EMPTY.applyFormat(format));
	}
}
