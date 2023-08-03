
JPanel pcurs = new JPanel();
Cursor [] curs = new Cursor [] {
   Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR),
   Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR),
   Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR),
   Cursor.getPredefinedCursor(Cursor.HAND_CURSOR),
   Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR),
   Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR),
   Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR),
   Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR),
   Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR),
   Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR),
   Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR),
   Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR),
   Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR),
   Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)
};
for (int i = 0; i < curs.length; i++) {
   Cursor cur = curs[i];
   JButton comp = new JButton(cur.getName());
   comp.setCursor(cur);
   pcurs.add(comp);
}

con.add(pcurs);

-----------------------------------------------------------------------------------------------------

Есть возможность создавать свои собственные курсоры.
Для этого в состав класса Toolkit введен метод создающий пользовательский курсор на основании указанного изображения, координат "горячей точки" и имени курсора.

final JButton btn_cust = new JButton("Custom Cursor");
con.add(btn_cust);
Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(
   new ImageIcon("C:\\tmp\\cursora.jpg").getImage(), new Point(5, 5), "foo_cursor"
);
btn_cust.setCursor(customCursor);

-----------------------------------------------------------------------------------------------------
-----------------------------------------------------------------------------------------------------
