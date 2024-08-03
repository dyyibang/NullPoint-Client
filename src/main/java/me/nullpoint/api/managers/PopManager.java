package me.nullpoint.api.managers;

import me.nullpoint.Nullpoint;
import me.nullpoint.api.events.eventbus.EventHandler;
import me.nullpoint.api.events.impl.DeathEvent;
import me.nullpoint.api.events.impl.PacketEvent;
import me.nullpoint.api.events.impl.TotemEvent;
import me.nullpoint.api.utils.Wrapper;
import me.nullpoint.mod.modules.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;

import java.util.ArrayList;
import java.util.HashMap;

public class PopManager implements Wrapper {
    public PopManager() {
        Nullpoint.EVENT_BUS.subscribe(this);
    }

    public final HashMap<String, Integer> popContainer = new HashMap<>();
    public final ArrayList<PlayerEntity> deadPlayer = new ArrayList<>();

    public Integer getPop(String s) {
        return popContainer.getOrDefault(s, 0);
    }
    public void update() {
        if (Module.nullCheck()) return;
        for (PlayerEntity player : mc.world.getPlayers()) {
            if (player == null || player.isAlive()) {
                deadPlayer.remove(player);
                continue;
            }
            if (deadPlayer.contains(player)) {
                continue;
            }
            Nullpoint.EVENT_BUS.post(new DeathEvent(player));
            onDeath(player);
            deadPlayer.add(player);
        }
    }

    @EventHandler
    public void onPacketReceive(PacketEvent.Receive event) {
        if (Module.nullCheck()) return;
        if (event.getPacket() instanceof EntityStatusS2CPacket packet) {
            if (packet.getStatus() == EntityStatuses.USE_TOTEM_OF_UNDYING) {
                Entity entity = packet.getEntity(mc.world);
                if(entity instanceof PlayerEntity player) {
                    onTotemPop(player);
                }
            }
        }
    }

    public void onDeath(PlayerEntity player) {
        popContainer.remove(player.getName().getString());
    }

    public void onTotemPop(PlayerEntity player) {
        int l_Count = 1;
        if (popContainer.containsKey(player.getName().getString())) {
            l_Count = popContainer.get(player.getName().getString());
            popContainer.put(player.getName().getString(), ++l_Count);
        } else {
            popContainer.put(player.getName().getString(), l_Count);
        }
        Nullpoint.EVENT_BUS.post(new TotemEvent(player));
    }
}
