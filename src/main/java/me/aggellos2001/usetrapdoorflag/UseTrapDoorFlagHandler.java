package me.aggellos2001.usetrapdoorflag;

import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.session.MoveType;
import com.sk89q.worldguard.session.Session;
import com.sk89q.worldguard.session.handler.FlagValueChangeHandler;
import com.sk89q.worldguard.session.handler.Handler;

public class UseTrapDoorFlagHandler extends FlagValueChangeHandler<StateFlag.State> {

	public static final Factory FACTORY = new Factory();

	public static class Factory extends Handler.Factory<UseTrapDoorFlagHandler> {
		@Override
		public UseTrapDoorFlagHandler create(final Session session) {
			return new UseTrapDoorFlagHandler(session);
		}
	}

	public UseTrapDoorFlagHandler(final Session session) {
		super(session, Main.TRAPDOOR_FLAG);
	}

	@Override
	protected void onInitialValue(final LocalPlayer player, final ApplicableRegionSet set, final StateFlag.State value) {

	}

	@Override
	protected boolean onSetValue(final LocalPlayer player, final Location from, final Location to, final ApplicableRegionSet toSet, final StateFlag.State currentValue, final StateFlag.State lastValue, final MoveType moveType) {
		return true;
	}

	@Override
	protected boolean onAbsentValue(final LocalPlayer player, final Location from, final Location to, final ApplicableRegionSet toSet, final StateFlag.State lastValue, final MoveType moveType) {
		return true;
	}
}
