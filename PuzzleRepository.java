package mensa;

import java.util.ArrayList;
import java.util.HashMap;

public class PuzzleRepository {
    private HashMap<String, Puzzle> puzzles;
    private HashMap<String, Integer> puzzleCnt;
    private ArrayList<Puzzle> puzzlesArr;

    public PuzzleRepository(PuzzleRepository pr){
        puzzles = new HashMap<>(pr.puzzles);
        puzzleCnt = new HashMap<>();
        pr.puzzleCnt.entrySet().stream().forEach((es) -> {puzzleCnt.put(es.getKey(), Integer.valueOf(es.getValue()));});
    }

    public PuzzleRepository(){
        puzzles = new HashMap<>();
        puzzleCnt = new HashMap<>();
    }

    public void add(Puzzle p){
        puzzles.put(p.getId(), p);
        puzzleCnt.merge(p.getId(), 1 , Integer::sum);
        puzzlesArr.add(p);
    }

    void delete(Puzzle p){
        if (! puzzles.containsKey(p.getId()))
            throw new IllegalStateException("no puzzle "+p.getId());
        puzzleCnt.merge(p.getId(), -1 , Integer::sum);
        puzzlesArr.remove(puzzlesArr.indexOf(p));
        if(puzzleCnt.get(p.getId()) <= 0){
            puzzleCnt.remove(p.getId());
            puzzles.remove(p.getId());
        }
    }

    Puzzle next(){
        return puzzlesArr.iterator().next();
    }

    boolean hasNext(){
        return puzzlesArr.iterator().hasNext();
    }

    Puzzle getById(String id){
        return puzzles.get(id);
    }

    public void generateSimplified1(){
        //13+11
        //12+16
        //14+8

        
        Puzzle t = new Puzzle("0");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(363,0));
        t.addPoint(new Point(0,363));
        t.setSidesIterable(3);
        t.calculate();
        add(t);

        t = new Puzzle("1");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(363,0));
        t.addPoint(new Point(0,363));
        t.setSidesIterable(3);
        t.calculate();
        add(t);

        t = new Puzzle("2");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(290,0));
        t.addPoint(new Point(0,290));
        t.setSidesIterable(3);
        t.calculate();
        add(t);

        t = new Puzzle("3");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(290,0));
        t.addPoint(new Point(0,290));
        t.setSidesIterable(3);
        t.calculate();
        add(t);

        t = new Puzzle("4");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(435,0));
        t.addPoint(new Point(150,292));
        t.addPoint(new Point(0,292));
        t.setSidesIterable(4);
        t.setTwoSided(true);
        t.calculate();
        add(t);

        t = new Puzzle("5");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(363,0));
        t.addPoint(new Point(50,404));
        t.setSidesIterable(3);
        t.calculate();
        add(t);

        t = new Puzzle("6");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(555,0));
        t.addPoint(new Point(160,160));
        t.setSidesIterable(3);
        t.setTwoSided(true);
        t.calculate();
        add(t);

        t = new Puzzle("7");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(555,0));
        t.addPoint(new Point(160,160));
        t.setSidesIterable(3);
        t.setTwoSided(true);
        t.calculate();
        add(t);


        t = new Puzzle("9");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(170,0));
        t.addPoint(new Point(210,360));
        t.addPoint(new Point(0,150));
        t.setSidesIterable(4);
        t.setTwoSided(true);
        t.calculate();
        add(t);

        t = new Puzzle("10");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(405,0));
        t.addPoint(new Point(100,300));
        t.setSidesIterable(3);
        t.setTwoSided(true);
        t.calculate();
        add(t);




        t = new Puzzle("12+16");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(440,0));
        t.addPoint(new Point(340,270));

        t.addPoint(new Point(155,350));
        t.setSidesIterable(4);
        t.setTwoSided(true);
        t.calculate();
        System.out.println(t);
        add(t);

        t = new Puzzle("13+11");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(365,0));
        t.addPoint(new Point(365,365));
        t.addPoint(new Point(0,365));
        t.setSidesIterable(1);
        t.calculate();
        add(t);

        t = new Puzzle("14+8");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(570,0));
        t.addPoint(new Point(220,360));
        t.addPoint(new Point(0,150));
        t.setSidesIterable(4);
        t.setTwoSided(true);
        t.calculate();
        add(t);

        t = new Puzzle("15");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(440,0));
        t.addPoint(new Point(360,160));
        t.addPoint(new Point(230,220));
        t.setSidesIterable(4);
        t.setTwoSided(true);
        t.calculate();
        add(t);


        t = new Puzzle("17");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(290,0));
        t.addPoint(new Point(290,150));
        t.addPoint(new Point(0,150));
        t.setSidesIterable(2);
        t.calculate();
        add(t);

        t = new Puzzle("18");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(145,0));
        t.addPoint(new Point(145,145));
        t.addPoint(new Point(0,145));
        t.setSidesIterable(1);
        t.calculate();
        add(t);
    }

    public  void generateAll(){

        Puzzle t = new Puzzle("0");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(363,0));
        t.addPoint(new Point(0,363));
        t.setSidesIterable(3);
        t.calculate();
        add(t);

        t = new Puzzle("1");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(363,0));
        t.addPoint(new Point(0,363));
        t.setSidesIterable(3);
        t.calculate();
        add(t);

        t = new Puzzle("2");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(290,0));
        t.addPoint(new Point(0,290));
        t.setSidesIterable(3);
        t.calculate();
        add(t);

        t = new Puzzle("3");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(290,0));
        t.addPoint(new Point(0,290));
        t.setSidesIterable(3);
        t.calculate();
        add(t);

        t = new Puzzle("4");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(435,0));
        t.addPoint(new Point(150,292));
        t.addPoint(new Point(0,292));
        t.setSidesIterable(4);
        t.setTwoSided(true);
        t.calculate();
        add(t);

        t = new Puzzle("5");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(363,0));
        t.addPoint(new Point(50,404));
        t.setSidesIterable(3);
        t.calculate();
        add(t);

        t = new Puzzle("6");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(555,0));
        t.addPoint(new Point(160,160));
        t.setSidesIterable(3);
        t.setTwoSided(true);
        t.calculate();
        add(t);

        t = new Puzzle("7");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(555,0));
        t.addPoint(new Point(160,160));
        t.setSidesIterable(3);
        t.setTwoSided(true);
        t.calculate();
        add(t);

        t = new Puzzle("8");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(200,0));
        t.addPoint(new Point(0,200));
        t.setSidesIterable(3);
        t.calculate();
        add(t);

        t = new Puzzle("9");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(170,0));
        t.addPoint(new Point(210,360));
        t.addPoint(new Point(0,150));
        t.setSidesIterable(4);
        t.setTwoSided(true);
        t.calculate();
        add(t);

        t = new Puzzle("10");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(405,0));
        t.addPoint(new Point(100,300));
        t.setSidesIterable(3);
        t.setTwoSided(true);
        t.calculate();
        add(t);

        t = new Puzzle("11");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(180,0));
        t.addPoint(new Point(180,180));
        t.addPoint(new Point(0,180));
        t.setSidesIterable(1);
        t.calculate();
        add(t);


        t = new Puzzle("12");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(440,0));
        t.addPoint(new Point(340,270));
        t.addPoint(new Point(210,220));
        t.addPoint(new Point(155,350));
        t.setSidesIterable(4);
        t.setTwoSided(true);
        t.calculate();
        add(t);

        t = new Puzzle("13");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(365,0));
        t.addPoint(new Point(365,180));
        t.addPoint(new Point(180,180));
        t.addPoint(new Point(180,365));
        t.addPoint(new Point(0,365));
        t.setSidesIterable(4);
        t.calculate();
        add(t);

        t = new Puzzle("14");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(570,0));
        t.addPoint(new Point(430,160));
        t.addPoint(new Point(220,160));
        t.addPoint(new Point(220,360));
        t.addPoint(new Point(0,150));
        t.setSidesIterable(4);
        t.setTwoSided(true);
        t.calculate();
        add(t);

        t = new Puzzle("15");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(440,0));
        t.addPoint(new Point(360,160));
        t.addPoint(new Point(230,220));
        t.setSidesIterable(4);
        t.setTwoSided(true);
        t.calculate();
        add(t);

        t = new Puzzle("16");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(144,0));
        t.addPoint(new Point(0,144));
        t.setSidesIterable(3);
        t.calculate();
        add(t);

        t = new Puzzle("17");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(290,0));
        t.addPoint(new Point(290,150));
        t.addPoint(new Point(0,150));
        t.setSidesIterable(2);
        t.calculate();
        add(t);

        t = new Puzzle("18");
        t.addPoint(new Point(0,0));
        t.addPoint(new Point(145,0));
        t.addPoint(new Point(145,145));
        t.addPoint(new Point(0,145));
        t.setSidesIterable(1);
        t.calculate();
        add(t);

        //generatePuzzleRot();

    }
}
