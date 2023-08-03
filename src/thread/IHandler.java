package thread;


import exception.SvjException;

/**
 * Простой обработчик
 * <BR/> С произвольным набором входных данных.
 * <BR/> Без приведения типов.
 * <BR/>
 * <BR/> - R - обьект ответа.
 * <BR/>
 * <BR/> User: svj
 * <BR/> Date: 29.07.2011 11:15:22
 */
public interface IHandler<R>
{
    public R handle ( Object... obj ) throws SvjException;
}
