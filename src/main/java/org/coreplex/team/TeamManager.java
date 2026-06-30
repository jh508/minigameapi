package org.coreplex.team;

import java.util.*;

public class TeamManager {

    private final Map<String, Team> teams = new LinkedHashMap<>();
    private final Map<UUID, Team> playerTeams = new HashMap<>();

    public void registerTeam(Team team)
    {
        teams.put(team.getId(), team);
    }

    public Optional<Team> getTeam(UUID uuid)
    {
        return Optional.ofNullable(playerTeams.get(uuid));
    }

    public Optional<Team> getTeamById(String id)
    {
        return Optional.ofNullable(teams.get(id));
    }

    public boolean assignToTeam(UUID uuid, String teamId)
    {
        Team team = teams.get(teamId);
        if (team == null || team.isFull()) return false;
        playerTeams.put(uuid, team);
        team.addMember(uuid);
        return true;
    }

    public void assignAllRandom(Collection<UUID> players)
    {
        for (UUID player : players)
        {
            getTeamWithSpace().ifPresent(team -> {
                team.addMember(player);
                playerTeams.put(player, team);
            });
        }
    }

    public void removePlayer(UUID uuid)
    {
        Team team = playerTeams.remove(uuid);
        if (team != null ) team.removeMember(uuid);
    }

    public boolean isTeamEliminated(String teamId)
    {
        Team team = teams.get(teamId);
        return team != null && team.isEmpty();
    }

    public Collection<Team> getTeams()
    {
        return Collections.unmodifiableCollection(teams.values());
    }

    private Optional<Team> getTeamWithSpace()
    {
        return teams.values().stream()
                .filter(team -> !team.isFull())
                .min(Comparator.comparingInt(team -> team.getMembers().size() ));
    }

}
