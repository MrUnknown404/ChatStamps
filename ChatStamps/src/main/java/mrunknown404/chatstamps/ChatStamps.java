package mrunknown404.chatstamps;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLNetworkConstants;

@Mod(ChatStamps.MOD_ID)
public class ChatStamps {
	public static final String MOD_ID = "chatstamps";
	
	public ChatStamps() {
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
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
			String s = null;
			
			try {
				s = new SimpleDateFormat(c.dateFormat.get()).format(new Date());
			} catch (@SuppressWarnings("unused") Exception e0) {
				System.out.println("Invalid date format!");
				return;
			}
			
			ITextComponent txt = string(c.startChar.get(), c.startCharColor.get()).append(string(s, c.timeColor.get())).append(string(c.endChar.get(), c.endCharColor.get()))
					.append(new StringTextComponent(" ").withStyle(Style.EMPTY)).append(e.getMessage());
			
			e.setMessage(txt);
		}
	}
	
	private static StringTextComponent string(String str, TextFormatting format) {
		return (StringTextComponent) new StringTextComponent(str).withStyle(Style.EMPTY.applyFormat(format));
	}
}
