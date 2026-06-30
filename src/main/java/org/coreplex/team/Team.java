package org.coreplex.team;

import net.kyori.adventure.text.format.NamedTextColor;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public class Team {
    private final String id;
    private final String displayName;
    private final NamedTextColor color;
    private final int maxSize;
    private final Set<UUID> members = new LinkedHashSet<>();

    public Team(String id, String displayName, NamedTextColor color, int maxSize) {
        this.id = id;
        this.displayName = displayName;
        this.color = color;
        this.maxSize = maxSize;
    }

    public boolean addMember(UUID uuid)
    {
        if(members.size() >= maxSize) return false;

        members.add(uuid);
        return true;
    }

    public void removeMember(UUID uuid)
    {
        members.remove(uuid);
    }

    public boolean isFull() { return members.size() >= maxSize; }
    public boolean isEmpty() { return members.isEmpty(); }
    public boolean hasMember(UUID uuid) { return members.contains(uuid); }

    public String getId() { return id; }
    public String getDisplayName() { return displayName; }
    public NamedTextColor getColor() { return color; }
    public int getMaxSize() { return maxSize; }
    public Set<UUID> getMembers() { return Collections.unmodifiableSet(members); }
}
