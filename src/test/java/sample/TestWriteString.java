package sample;

import asia.kala.ansi.AnsiString;
import io.ib67.tidget.Tidget;
import io.ib67.tidget.TidgetCanvas;
import io.ib67.tidget.TidgetProperty;
import io.ib67.tidget.TidgetRenderer;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

public class TestWriteString {
    @Test
    public void testWriteString() throws InterruptedException {
        var tidget = new Tidget(
                new TidgetProperty(
                        30,
                        5,
                        AnsiString.ofPlain(" ").overlay(AnsiString.Back.LightBlue).toString() // populator
                ),
                System.out
        );
        class SimpleRenderer extends TidgetRenderer{
            protected SimpleRenderer() {
                super(0,null);
            }

            @Override
            public boolean useSharedCanvas() {
                return true;
            }

            @Override
            protected void onDraw(TidgetCanvas canvas) {
                canvas.drawString( ThreadLocalRandom.current().nextFloat(0.9F),ThreadLocalRandom.current().nextFloat(1),"Hello World!");
            }
        }
        tidget.registerRenderer(new SimpleRenderer());
        for (int i = 0; i < 300; i++) {
            tidget.render();
            Thread.sleep(20L);
        }
    }
}
