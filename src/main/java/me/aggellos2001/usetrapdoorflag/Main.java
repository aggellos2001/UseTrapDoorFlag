package me.aggellos2001.usetrapdoorflag;


import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.StringUtil;

public class Main extends JavaPlugin implements Listener {

	public static StateFlag TRAPDOOR_FLAG;

	@Override
	public void onLoad() {

		final var worldGuardRegistry = WorldGuard.getInstance().getFlagRegistry();
		try {
			final var flag = new StateFlag("use-trapdoor", true);
			worldGuardRegistry.register(flag);
			TRAPDOOR_FLAG = flag;
			this.getLogger().info("Flag UseTrapDoor registered!");
		} catch (final FlagConflictException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler
	private void checkFlag(final PlayerInteractEvent event) {

		final var player = event.getPlayer();
		final var adapterWorld = BukkitAdapter.adapt(player.getLocation().getWorld());
		final var adaptedLocation = BukkitAdapter.adapt(player.getLocation());
		final var adaptedPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
		final var block = event.getClickedBlock();

		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if (!(block.getBlockData() instanceof TrapDoor)) return;

		final var canBypass = WorldGuard.getInstance().getPlatform().getSessionManager().hasBypass(adaptedPlayer, adapterWorld);

		final var container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		final var useTrapDoorFlagState = container.createQuery().queryState(adaptedLocation, adaptedPlayer, TRAPDOOR_FLAG);
		final var worldGuardDeny = container.createQuery().queryValue(adaptedLocation, adaptedPlayer, Flags.DENY_MESSAGE) != null
				? container.createQuery().queryValue(adaptedLocation, adaptedPlayer, Flags.DENY_MESSAGE)
				: "You can't interact with trapdoors here!";
		final var denyMessage = StringUtils.replace(worldGuardDeny,"%what%","use trapdoors");
		if (useTrapDoorFlagState == StateFlag.State.DENY && !canBypass) {
			player.sendMessage(denyMessage);
			event.setCancelled(true);
		}
	}
}
