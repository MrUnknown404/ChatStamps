package mrunknown404.chatstamps.utils;

import mrunknown404.chatstamps.Main;
import mrunknown404.unknownlibs.utils.ColorUtils.ColorCodes;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Main.MOD_ID)
@Config.LangKey(Main.MOD_ID + ".config.title")
public class ModConfig {
	@Config.Comment("Date format (example 'h:mm a', Information can be found here 'https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html')")
	public static String dateFormat = "h:mm a";
	@Config.Comment("Time Color (example 'WHITE')")
	public static ColorCodes timeColor = ColorCodes.WHITE;
	@Config.Comment("Start characters (example '[', supports ColorCodes)")
	public static String startChars = "[";
	@Config.Comment("End characters (example ']', supports ColorCodes)")
	public static String endChars = "]";
	@Config.Comment("Whether or not to add a timestamps to commands")
	public static boolean addTimeToNonMessages = false;
	
	@EventBusSubscriber(modid = Main.MOD_ID)
	private static class EventHandler {
		@SubscribeEvent
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent e) {
			if (e.getModID().equals(Main.MOD_ID)) {
				ConfigManager.sync(Main.MOD_ID, Config.Type.INSTANCE);
			}
		}
	}
}
