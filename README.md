# Tidget
A simple utility to draw widgets on the terminal.

# Example
https://asciinema.org/a/PF2U9Bl0bJBIKwUHCQKGZaDYW
```java
    var tidget = new Tidget(
                new TidgetProperty(
                        30, // width
                        5, // height
                        " " // populator
                ),
                System.out // where to print
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
```