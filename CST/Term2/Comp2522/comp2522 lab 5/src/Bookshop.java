import java.util.*;

public class Bookshop {
    private final Map<String, Novel> novelMap;

    public Bookshop()
    {
        novelMap = new HashMap<>();
        addNovel(new Novel("The Adventures of Augie March", "Saul Bellow", 1953));
        addNovel(new Novel("Wide Sargasso Sea", "Jean Rhys", 1966));

        Set<String> keys = novelMap.keySet();

        System.out.println("All titles:");
        Iterator<String> iterator = keys.iterator();
        while(iterator.hasNext()) {
            String title = iterator.next();
            System.out.println(title);
        }

        iterator = keys.iterator();
        while(iterator.hasNext()) {
            String title = iterator.next();
            if(title.toLowerCase().contains("the")) {
                iterator.remove();
            }
        }

        Set<String> keySet = novelMap.keySet();
        List<String> keyList = new ArrayList<>(keySet);
        Collections.sort(keyList);
        System.out.println("\nTitles after removing 'the' and sorted:");
        for(String title : keyList) {
            Novel novel = novelMap.get(title);
            System.out.println(novel);
        }
    }

    private void addNovel(Novel novel)
    {
        novelMap.put(novel.getTitle(), novel);
    }

    public static void main(String[] args)
    {
        new Bookshop();
    }
}
