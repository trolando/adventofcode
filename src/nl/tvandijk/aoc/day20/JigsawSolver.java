package nl.tvandijk.aoc.day20;

import java.util.List;
import java.util.stream.Collectors;

public class JigsawSolver {
    public static List<Block> solve(List<Tile> tiles) {
        var fitter = new TileFitter(tiles);
        int numberOfTiles = tiles.size() / 8;
        int N = (int) Math.sqrt(numberOfTiles);

        // MAKE CORNERS
        List<Block> tlcorners = fitter.getTLCorners().stream()
                .map(x -> new Block(new Tile[]{x}, 1, 1))
                .collect(Collectors.toList());

        List<Block> blcorners = fitter.getBLCorners().stream()
                .map(x -> new Block(new Tile[]{x}, 1, 1))
                .collect(Collectors.toList());

        List<Block> trcorners = fitter.getTRCorners().stream()
                .map(x -> new Block(new Tile[]{x}, 1, 1))
                .collect(Collectors.toList());

        List<Block> brcorners = fitter.getBRCorners().stream()
                .map(x -> new Block(new Tile[]{x}, 1, 1))
                .collect(Collectors.toList());

        // MAKE EDGES
        List<Block> ledges = fitter.getLEdges().stream()
                .map(x -> new Block(new Tile[]{x}, 1, 1))
                .collect(Collectors.toList());

        List<Block> redges = fitter.getREdges().stream()
                .map(x -> new Block(new Tile[]{x}, 1, 1))
                .collect(Collectors.toList());

        List<Block> tedges = fitter.getTEdges().stream()
                .map(x -> new Block(new Tile[]{x}, 1, 1))
                .collect(Collectors.toList());

        List<Block> bedges = fitter.getBEdges().stream()
                .map(x -> new Block(new Tile[]{x}, 1, 1))
                .collect(Collectors.toList());

        // MAKE NEDGES
        List<Block> nedges = fitter.getNEdges().stream()
                .map(x -> new Block(new Tile[]{x}, 1, 1))
                .collect(Collectors.toList());

        // MAKE TOP ROW
        List<Block> toprow = tlcorners;
        for (int i = 1; i < N; i++) {
            if ((i + 1) < N) {
                toprow = toprow.stream().flatMap(block -> block.extendRight(fitter, tedges).stream())
                        .collect(Collectors.toList());
            } else {
                toprow = toprow.stream().flatMap(block -> block.extendRight(fitter, trcorners).stream())
                        .collect(Collectors.toList());
            }
        }

        // MAKE MIDDLE ROW
        List<Block> midrow = ledges;
        for (int i = 1; i < N; i++) {
            if ((i + 1) < N) {
                midrow = midrow.stream().flatMap(block -> block.extendRight(fitter, nedges).stream())
                        .collect(Collectors.toList());
            } else {
                midrow = midrow.stream().flatMap(block -> block.extendRight(fitter, redges).stream())
                        .collect(Collectors.toList());
            }
        }

        // MAKE BOTTOM ROW
        List<Block> botrow = blcorners;
        for (int i = 1; i < N; i++) {
            if ((i + 1) < N) {
                botrow = botrow.stream().flatMap(block -> block.extendRight(fitter, bedges).stream())
                        .collect(Collectors.toList());
            } else {
                botrow = botrow.stream().flatMap(block -> block.extendRight(fitter, brcorners).stream())
                        .collect(Collectors.toList());
            }
        }

        List<Block> board = toprow;
        for (int i = 1; i < N; i++) {
            if ((i + 1) < N) {
                List<Block> finalMidrow = midrow;
                board = board.stream().flatMap(block -> block.extendDown(fitter, finalMidrow).stream())
                        .collect(Collectors.toList());
            } else {
                List<Block> finalBotRow = botrow;
                board = board.stream().flatMap(block -> block.extendDown(fitter, finalBotRow).stream())
                        .collect(Collectors.toList());
            }
        }

        return board;
    }
}
