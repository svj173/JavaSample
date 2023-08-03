 Примеры

									REGEXP_remote_user      = "^[a-zA-Z0-9][-_0-9a-zA-Z]{0,64}",
									REGEXP_remote_password  = ".*",
									REGEXP_remote_host      = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$",
									REGEXP_remote_path      = "[/\\.-_0-9a-zA-Z]{0,264}",
									REGEXP_remote_port      = "^(([0-9]{1,4})|([1-5][0-9]{4})|(6[0-4][0-9]{3})|(65[0-4][0-9]{2})|(655[0-2][0-9])|(6553[0-5]))$";
REGEXP_keep_days = "[1-9][0-9]{0,1}",

    public static final String LANG_FILENAME_REGEXP = "^.*_lang_[a-z]{2}_[A-Z]{2}[.]xml$";
    public static final String LOCALE_NAME_REGEXP = "^[a-z]{2}_[A-Z]{2}$";
    public static final String LOCALE_NAME_MATCHE = "_lang_[a-z]{2}_[A-Z]{2}[.]";

    ** Разрешены
     * Латинские буквы, цифры
     * Символы: решетка, плюсик, пробел, подчеркивание, дефис
     *
    public static final String KEY_REGEXP = "^[a-zA-Z0-9#+ //._//-]+$";

    ** Запрещены русские символы
     *
    public static final String HAS_NO_RUS_LETTERS = "[^а-яА-Я]*";

        regexp = "^(6[0-9]{1,15}|[1-9][0-9]{2,16})$";     // 60..69, 100..
        regexp = "^[1-9][0-9]{0,16}";     // от 1 ...


"^([1-9][0-9]{1,})$" -  не может быть меньше 10 ms.
^([1-9][0-9][0-9][0-9]{1,})$  - не может быть меньше 1000 ms.

 от "45" до "567" получилось:
\b0*(4[5-9]|[5-9][0-9]|[1-4][0-9]{2}|5[0-5][0-9]|56[0-7])\b

^(6[0-9]{1,2}|7[0-9]{1,2}|8[0-9]{1,2}|9[0-9]{1,2}|[1-9][0-9]{2,16})$   - от 60 и выше - 16 символов

^\d{1,5}$  - макс - пятизначное - от 0 до 99999
^\d{3,4}$  - от 100 до 9999

Сайты

On-line
1) https://regex101.com/
2) http://www.regexr.com/
3) http://ww17.pcreonline.com/MrKMAf-2/
Desctop
1) Expresso
