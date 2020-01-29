package mrunknown404.chatstamps;

import java.text.SimpleDateFormat;
import java.util.Date;

import mrunknown404.chatstamps.utils.ModConfig;
import mrunknown404.unknownlibs.utils.ColorUtils;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = Main.MOD_ID, clientSideOnly = true, useMetadata = true, dependencies = "required-after:unknownlibs@[1.0.1,)")
public class Main {
	public static final String MOD_ID = "chatstamps";
	
	@Instance
	public static Main main;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onChatReceived(ClientChatReceivedEvent e) {
		if (e.getType() == ChatType.CHAT || ModConfig.addTimeToNonMessages) {
			String s = new SimpleDateFormat(ModConfig.dateFormat).format(new Date());
			ITextComponent txt = new TextComponentString(ColorUtils.addColor(ModConfig.startChars + "&r" + "&" + ModConfig.timeColor.code + s + "&r" + ModConfig.endChars + "&r "))
					.appendSibling(e.getMessage());
			
			e.setMessage(txt);
		}
	}
}
