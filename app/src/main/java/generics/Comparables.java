package generics;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import java.util.Comparator;

/**
 Different examples for Comparable<T> and Comparator<T> for comparision strategy.
 */
public class Comparables {
    public static void main(String[] args) {
        List<Player> team = new ArrayList<>();
        team.add(new Player(59, "John", 20)); 
        team.add(new Player(67, "Roger", 22));
        team.add(new Player(45, "Steven", 24));

        // Using Comparable Approach
        System.out.println("Before Sorting: " + team);
        Collections.sort(team); // will throw compile-error without comparision strategy
        // To avoid compile-error, we need to implement Comparable interface for POJO that
        // that need to compared.
        System.out.println("After Sorting: " + team);

        // Using Comparator approach
        Comparator<Player> playerComparator = new PlayerRankingComparator();
        print(team, playerComparator);

        playerComparator = new PlayerAgeComparator();
        print(team, playerComparator);

        // Java 8 Comparator with Lambdas
        Comparator<Player> byRanking = (Player player1, Player player2) ->
                 Integer.compare(player1.getRanking(), player2.getRanking());

        // more concise approach for above line for integers
        Comparator<Player> byRanking2 = (p1, p2) -> p1.getRanking() - p2.getRanking();
        print(team, byRanking2);

        
        // another java 8 approach
        Comparator<Player> byRanking3 = Comparator.comparing(Player::getRanking);
        Comparator<Player> byAge = Comparator.comparing(Player::getAge);
        print(team, byRanking2);
        print(team, byAge);
    }

    private static void print(List<Player> team, Comparator<Player> comparator) {
        System.out.println("Before Sorting: " + team);
        Collections.sort(team, comparator);
        System.out.println("After Sorting: " + team);
    }
}

class Player implements Comparable<Player> {
    private int ranking, age;
    private String name;
    public Player(int ranking, String name, int age) {
        this.ranking = ranking;
        this.age = age;
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ranking;
        result = prime * result + age;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Player other = (Player) obj;
        if (ranking != other.ranking)
            return false;
        if (age != other.age)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public int compareTo(Player player) {
        return Integer.compare(getRanking(), player.getRanking());
    }
    public int getRanking() {
        return ranking;
    }
    public void setRanking(int ranking) {
        this.ranking = ranking;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}

class PlayerRankingComparator implements Comparator<Player> {

    @Override
    public int compare(Player firstPlayer, Player secondPlayer) {
       return Integer.compare(firstPlayer.getRanking(), secondPlayer.getRanking());
    }
}

class PlayerAgeComparator implements Comparator<Player> {

    @Override
    public int compare(Player firstPlayer, Player secondPlayer) {
       return Integer.compare(firstPlayer.getAge(), secondPlayer.getAge());
    }
}
