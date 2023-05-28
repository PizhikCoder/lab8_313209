package server.visualizationhandlers;

import javafx.animation.Timeline;
import server.core.Invoker;
import shared.core.models.MusicBand;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CirclesProcessor {
    private final Invoker invoker;

    private final Logger logger = Logger.getLogger(getClass().getName());

    private volatile static CirclesProcessor circlesProcessor;

    private final ExecutorService executorService;

    private final ForkJoinPool forkJoinPool;

    private CirclesProcessor(Invoker invoker){
        this.invoker = invoker;
        executorService = Executors.newSingleThreadExecutor();
        this.forkJoinPool = new ForkJoinPool();
    }

    public synchronized static CirclesProcessor getInstance(Invoker invoker){
        if (circlesProcessor == null){
            circlesProcessor = new CirclesProcessor(invoker);
        }
        return circlesProcessor;
    }

    public void process(MusicBand musicBand){
        executorService.submit(()->{

            CirclesMover circlesMover = new CirclesMover(invoker.getModelsManager(), invoker);
            forkJoinPool.invoke(new RecursiveAction() {
                @Override
                protected void compute() {
                    try {
                        circlesMover.startMoving(musicBand);
                    } catch (InterruptedException e) {
                        logger.log(Level.WARNING, "Exception while circles moving.", e);
                    }
                    logger.log(Level.INFO, "Checking finished!");
                }
            });
        });
    }
}
