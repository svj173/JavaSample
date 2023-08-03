package grafics.grafics2;


    import java.awt.*;
    import java.awt.event.*;
    import java.awt.font.TextLayout;
    import java.awt.geom.*;
    import java.awt.image.MemoryImageSource;
    import javax.swing.*;
    
    class myComponent extends JComponent {
        private static final int N = 300;
    
        public Dimension getPreferredSize() {
            return new Dimension(N, N);
        }
    
        public void paint(Graphics g)
        {
            Graphics2D g2d = (Graphics2D)g;

            // управление параметрами рисования – рендеринга.
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            
            // перевернуть все дальнейшее изображение
            //AffineTransform aft = AffineTransform.getRotateInstance(Math.PI, N / 2, N / 2);
            //g2d.setTransform(aft);

            /*

public void paint(Graphics g) {
          super.paint(g);
          g.setColor(Color.BLUE); // устанавливаем текущий цвет
          g.drawLine(0,0 ,getSize().width , getSize().height);// рисуем линию через весь размер экрана
          g.setColor(new Color (128,128,128));
          g.drawOval(getSize().width/2 , getSize().height/2, 100, 50);
          g.setColor(new Color (0xFFFF00A0, true));
          g.drawPolygon(
                new Polygon(new int []{0,100,200,300}, new int []{0 , 200, 300, 100}, 4)
          );

          g.drawRoundRect(60 , 60 , 210 , 210 , 50 , 10);
          g.drawString("Hello From Java 2D", 50 , 50);
          g.fillRect(20 , 20 , 60 , 60);
       }
            
g.drawRect(10 , 10 , 100 , 100); // рисуем квадрат со стороной 100 px.
 g.translate(10 , 10); // перемещаем систему координат в точку 10, 10
 g.drawRect(0,0,100,100);// рисуем квадрат совпадающий с ранее нарисованным


 // Шрифты
 g.setColor (Color.BLUE);
GraphicsEnvironment gEnv =
  GraphicsEnvironment.getLocalGraphicsEnvironment();
String envfonts[] = gEnv.getAvailableFontFamilyNames();
setTitle("Общее количество шрифтов: " + envfonts.length);
int w = getSize().width, h = getSize().height;
int start_h = 0;
for (int i = 0; i < envfonts.length; i++){
  Font f =  new Font (envfonts[i] , Font.ITALIC, 12);
  g.setFont(f);

  FontMetrics metrics = g.getFontMetrics();
  int width = metrics.stringWidth( envfonts[i] );
  int height = metrics.getLeading() + metrics.getHeight();
  g.drawString( envfonts[i], w/2-width/2, start_h + height );
  start_h += height;
}
             */
            
            Font f = new Font("monospaced", Font.BOLD, 24);
            g2d.setFont(f);
            String s = "testing";
            g2d.drawString(s, 100, 100);

            // линия
            FontMetrics fm = getFontMetrics(f);
            int h = fm.getHeight();
            int w = fm.stringWidth(s);
            g2d.drawLine(100, 100 + h, 100 + w, 100 + h);

            // Трехмерный прямоугольник с закругленными краями. true  -тень спарва снаружи. false - тень внутри слева. Толщина тени - 1 пикс.
            g2d.setColor ( Color.GREEN );
            g2d.draw3DRect ( 10, 100, 20, 70, true );
  /*
            // изображение из файла
            MemoryImageSource msource = new MemoryImageSource (
 4,7,
 new int []{
  0xFFFF0000,0xFFFF0000,0xFFFF0000,0xFFFF0000,
  0xFFFF0000,0xFF00FF00,0xFF00FF00,0xFFFF0000,
  0xFFFF0000,0xFF00FF00,0xFF00FF00,0xFFFF0000,
  0xFFFF0000,0xFF0000FF,0xFF0000FF,0xFFFF0000,
  0xFFFF0000,0xFF00FF00,0xFF00FF00,0xFFFF0000,
  0xFFFF0000,0xFF0000FF,0xFF0000FF,0xFFFF0000,
  0xFFFF0000,0xFFFF0000,0xFFFF0000,0xFFFF0000
 }
 , 0, 4
);
Image img = getToolkit().createImage(msource);
 g2d.drawImage(img, 40, 40 , 50*4, 50*7, this);
*/
/*
Трансформация фигур, текста и изображений

Возвращаясь к параметрам управляющими Graphics2D, рассмотрим как можно трансформировать объекты.
Вы можете выполнять над графическими объектами операции: перемещение, вращение, масштабирование, усечение объектов на стадии рендеринга.
Все эти преобразования задаются с помощью объектов производных от AffineTransform.             
             */
// вывод текста с разными сдвигами и наклонами
            /*
TextLayout textTl = new TextLayout ("Hello From J",
    new Font("Helvetica", Font.ITALIC, 48),
    g2d.getFontRenderContext());
g2d.setColor ( Color.BLUE );
textTl.draw(g2d , 50 , 150);

// создаем матрицу вращения
AffineTransform rot =  AffineTransform.getRotateInstance (Math.PI / 12 , 100,100);
// масштабирования
AffineTransform scal = AffineTransform.getScaleInstance (0.5 , 0.33);
// урезания
AffineTransform shear = AffineTransform.getShearInstance (0.5 , 0.33);
// перемещения
AffineTransform transl = AffineTransform.getTranslateInstance(50 , -70);
// применяем матрицу к графическому контексту
g2d.setTransform(rot);
// и чего-то рисуем
            g2d.setColor ( Color.GRAY );
textTl.draw(g2d , 60 , 150);

AffineTransform trans = AffineTransform.getRotateInstance(0);
trans.rotate(Math.PI / 6, 100 , 100);
trans.scale(1.5, 0.33);
trans.translate(15 , 40);
g2d.setTransform(trans);
            g2d.setColor ( Color.BLACK );
textTl.draw(g2d , 70 , 150);
              */
/*
g.setColor(Color.BLUE);
Shape s = new QuadCurve2D.Double (50,50, 25, 78, 120 , 80);
((Graphics2D) g).draw(s);

GeneralPath path = new GeneralPath(GeneralPath.WIND_EVEN_ODD,12);
path.reset();
double alp30 = Math.PI / 6;
path.moveTo(0,0);
int cx = getSize().width / 2;
int cy = getSize().height / 2;
path.moveTo((float)(cx + 100), cy);

for (int i = 1; i <= 12; i++){
  float x  = (float)(cx + 100 * Math.cos (i*alp30));
  float y  = (float)(cy + 100 * Math.sin (i*alp30));
  path.lineTo(x,y);
}

((Graphics2D) g).draw(path);

Особый интерес представляет собой комбинирование фигур – для этого используется класс Area:

Area ar = new Area(path);
ar.exclusiveOr(new Area (new Ellipse2D.Double(100 , 100 , 50 , 50)));
((Graphics2D) g).draw(ar);

В составе класса Area есть методы для всех булевых операций над объектами:

Area ar = new Area(path);
Area rhs = new Area (new Ellipse2D.Double(100 , 100 , 50 , 50));
ar.exclusiveOr(rhs); // операция XOR исключающего ИЛИ
ar.add(rhs); // операция объединения фигур
ar.intersect(rhs); // пересечение фигур
ar.subtract(rhs); // разница фигур
            
             */

// Теперь попробуем нарисовать заливку прямоугольника.
// В первом случае используется сплошная красная заливка, во втором же градиент, переходящий от красного (в левом верхнем углу) до белого (в правом нижнем):

g2d.setPaint(Color.blue);
 g2d.fill(new Rectangle2D.Double(20, 20,100, 100));
 // и второй – с градиентом
 GradientPaint redtowhite = new GradientPaint(20,130,Color.RED,20+100, 130+100,Color.WHITE);
 g2d.setPaint(redtowhite);
 g2d.fill(new Rectangle2D.Double(20, 130,100, 100));

/*
// пунктирное рисование линий
g.setColor(Color.BLUE);
 BasicStroke dashed = new BasicStroke(5.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
    10.0f, new float []{10.0f, 20.f, 50.0f}, 0.0f);
 Graphics2D g2 = (Graphics2D) g;
 g2.setStroke(dashed);
 g2.draw(new Ellipse2D.Double(100 , 100 , 200 , 200));

Здесь используется полная версия конструктора BasicStroke: первый параметр – толщина линии. Второй параметр - стиль линии на окончании.
Третий параметр – тип линии используемой при соединении двух фрагментов.
Пятый параметр - здесь вы должны указать стиль чередования штриховки – элементы массива задают, то сплошную линию, то пропуск.
Следующий параметр - начальное состояние линии штриховки. 

             */
        }
    }
    
    public class graph2d {
        public static void main(String args[]) {
            JFrame f = new JFrame("testing");
            f.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });
    
            JPanel p = new JPanel();
            p.add(new myComponent());
            f.getContentPane().add(p);
    
            f.pack();
            f.setVisible(true);
        }
    }
