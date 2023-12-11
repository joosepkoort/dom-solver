package com.dom.solver;

import com.dom.solver.services.GameService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;

@SpringBootApplication
@Configuration
@EnableTransactionManagement
@ComponentScan()
public class DragonApplication implements CommandLineRunner {

    @Resource
    private GameService gameService;

    private static Logger logger = LoggerFactory
            .getLogger(DragonApplication.class);

    public static void main(String[] args) {
        //displayDbGui();
        SpringApplication.run(DragonApplication.class, args);
    }

    /**
     * If we want to display the database contents, we can use the following method
     */
    private static void displayDbGui() {
        new org.hsqldb.util.DatabaseManagerSwing().main(new String[]{"--url", "jdbc:hsqldb:mem:dom;DB_CLOSE_DELAY=-1", "--user", "sa", "--password", ""});
    }

    @Override
    public void run(String... args) throws IOException {
        for (int i = 0; i < args.length; ++i) {
            logger.info("args[{}]: {}", i, args[i]);
            if (args[i].equals("start")) {
                System.out.println("Starting game.");
                for (int y = 1; y <= Integer.parseInt(args[i + 1]); ++y) {
                    System.out.println("Playing game " + y);
                    gameService.startGame(y);
                }
            }
            System.out.println("Games finished.");
            System.exit(0);
        }
    }
}