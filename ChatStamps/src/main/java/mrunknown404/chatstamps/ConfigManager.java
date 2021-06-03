package mrunknown404.chatstamps;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.tuple.Pair;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

public class ConfigManager {
	static final ConfigManager INSTANCE;
	static final ForgeConfigSpec SPEC;
	
	private static final Path CONFIG_PATH = Paths.get("config", ChatStamps.MOD_ID + ".toml");
	
	static {
		Pair<ConfigManager, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ConfigManager::new);
		INSTANCE = specPair.getLeft();
		SPEC = specPair.getRight();
		CommentedFileConfig config = CommentedFileConfig.builder(CONFIG_PATH).sync().autoreload().writingMode(WritingMode.REPLACE).build();
		config.load();
		config.save();
		SPEC.setConfig(config);
	}
	
	final ConfigValue<String> startChar, endChar, dateFormat;
	final ConfigValue<TextFormatting> startCharColor, endCharColor, timeColor;
	final BooleanValue addTimeToNonMessages;
	
	private ConfigManager(ForgeConfigSpec.Builder configSpecBuilder) {
		startChar = configSpecBuilder.translation(ChatStamps.MOD_ID + "config.startChar").comment("Start characters (example '[')").define("startChar", "[");
		endChar = configSpecBuilder.translation(ChatStamps.MOD_ID + "config.endChar").comment("End characters (example '[')").define("endChar", "]");
		dateFormat = configSpecBuilder.translation(ChatStamps.MOD_ID + "config.dateFormat")
				.comment("Date format (example 'h:mm a', Information can be found here 'https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html')")
				.define("dateFormat", "h:mm a");
		addTimeToNonMessages = configSpecBuilder.translation(ChatStamps.MOD_ID + "config.addTimeToNonMessages").comment("Whether or not to add a timestamps to commands")
				.define("addTimeToNonMessages", false);
		startCharColor = configSpecBuilder.translation(ChatStamps.MOD_ID + "config.startCharColor").comment("Star char Color (example 'WHITE')").defineEnum("startCharColor",
				TextFormatting.WHITE);
		endCharColor = configSpecBuilder.translation(ChatStamps.MOD_ID + "config.endCharColor").comment("End char Color (example 'WHITE')").defineEnum("endCharColor",
				TextFormatting.WHITE);
		timeColor = configSpecBuilder.translation(ChatStamps.MOD_ID + "config.timeColor").comment("Time Color (example 'WHITE')").defineEnum("timeColor", TextFormatting.WHITE);
	}
}
