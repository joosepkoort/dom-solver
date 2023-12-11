package com.dom.solver.services;

import com.dom.solver.model.GameStatus;
import lombok.RequiredArgsConstructor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ChartService {
    private static final String TITLE = "Game statistics";
    private static final String X_AXIS = "Steps";

    @Value("${statistics.path}")
    private String path;
    @Value("${statistics.enabled}")
    private boolean statisticsEnabled;

    protected void saveCharts(GameStatus gameStatus) throws IOException {
        if (statisticsEnabled) {
            Files.createDirectories(Paths.get(path));
            saveDifficultyChart(gameStatus);
            saveGoldChart(gameStatus);
            saveScoreChart(gameStatus);
        }
    }

    private void saveScoreChart(GameStatus gameStatus) throws IOException {
        XYSeries series = new XYSeries("Score");
        for (int i = 0; i < gameStatus.getStatisticsList().size(); i++) {
            series.add(i, gameStatus.getStatisticsList().get(i).getScore());
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        JFreeChart chart = ChartFactory.createXYLineChart(
                TITLE + "-" + gameStatus.getGameId(),
                X_AXIS,
                "Score",
                dataset
        );
        chart.getXYPlot().setRenderer(new org.jfree.chart.renderer.xy.XYLineAndShapeRenderer());
        chart.getXYPlot().getRenderer().setSeriesPaint(0, Color.BLUE);
        chart.getXYPlot().getRenderer().setSeriesStroke(0, new BasicStroke(2.0f));
        ChartUtils.saveChartAsPNG(new File(path + gameStatus.getGameId() + "_score_chart.png"), chart, 500, 300);
    }

    private void saveDifficultyChart(GameStatus gameStatus) throws IOException {
        XYSeries series = new XYSeries("Difficulty");
        for (int i = 0; i < gameStatus.getStatisticsList().size(); i++) {
            series.add(i, gameStatus.getStatisticsList().get(i).getDifficulty());
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        JFreeChart chart = ChartFactory.createXYLineChart(
                TITLE + "-" + gameStatus.getGameId(),
                X_AXIS,
                "Difficulty",
                dataset
        );
        chart.getXYPlot().setRenderer(new org.jfree.chart.renderer.xy.XYLineAndShapeRenderer());
        chart.getXYPlot().getRenderer().setSeriesPaint(0, Color.BLUE);
        chart.getXYPlot().getRenderer().setSeriesStroke(0, new BasicStroke(2.0f));
        ChartUtils.saveChartAsPNG(new File(path + gameStatus.getGameId() + "_difficulty_chart.png"), chart, 500, 300);
    }

    private void saveGoldChart(GameStatus gameStatus) throws IOException {
        XYSeries series = new XYSeries("Gold");
        for (int i = 0; i < gameStatus.getStatisticsList().size(); i++) {
            series.add(i, gameStatus.getStatisticsList().get(i).getGold());
        }
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        JFreeChart chart = ChartFactory.createXYLineChart(
                TITLE + "-" + gameStatus.getGameId(),
                X_AXIS,
                "Gold",
                dataset
        );
        chart.getXYPlot().setRenderer(new org.jfree.chart.renderer.xy.XYLineAndShapeRenderer());
        chart.getXYPlot().getRenderer().setSeriesPaint(0, Color.BLUE);
        chart.getXYPlot().getRenderer().setSeriesStroke(0, new BasicStroke(2.0f));
        ChartUtils.saveChartAsPNG(new File(path + gameStatus.getGameId() + "_gold_chart.png"), chart, 500, 300);
    }
}
