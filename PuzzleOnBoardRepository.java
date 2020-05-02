package mensa;

import java.util.HashMap;

//todo
public class PuzzleOnBoardRepository {

    private HashMap<String, PuzzleOnBoard> repo;
    private PuzzleRepository puzzleRepository;

    public void setPuzzleRepository(PuzzleRepository puzzleRepository) {
        this.puzzleRepository = puzzleRepository;
    }

    private PuzzleOnBoardRepository() {  repo = new HashMap<>();  }
    private static class PuzzleOnBoardRepositoryHelper{
      private static final PuzzleOnBoardRepository INSTANCE = new PuzzleOnBoardRepository();
    }

    public static PuzzleOnBoardRepository getInstance() {return PuzzleOnBoardRepositoryHelper.INSTANCE; }

    PuzzleOnBoard getOrConstruct(String puzzleId, float angle, Point vector, int centerPointId, boolean fliped){
        String coordinates = PuzzleOnBoard.generateCoordinates(puzzleId, angle, vector, centerPointId, fliped);
        if (repo.containsKey(coordinates)){
            return repo.get(coordinates);
        }else{
            PuzzleOnBoard puzzleOnBoard = new PuzzleOnBoard();
            puzzleOnBoard = puzzleOnBoard.takePuzzle(puzzleRepository.getById(puzzleId))
                    .flip(fliped)
                    .rotate(angle)
                    .aroundPoint(centerPointId)
                    .move(vector)
                    .placeOnBoard();
            repo.put(puzzleOnBoard.getCoordinates(), puzzleOnBoard);
            return puzzleOnBoard;
        }


    }

    boolean contains(String puzzleId, float angle, Point vector, int centerPointId, boolean fliped){
       return repo.containsKey(PuzzleOnBoard.generateCoordinates(puzzleId, angle, vector, centerPointId, fliped));
    }

    void put(PuzzleOnBoard p){
        repo.put(p.getCoordinates(), p);
    }

    /*PuzzleOnBoard get(String coordinates){
        if(repo.containsKey(coordinates)){
            return repo.get(coordinates);
        }else{
            PuzzleOnBoard puzzleOnBoard = new PuzzleOnBoard()
        }

    }*/
}
