package com.premium.aboutyou.data

/**
 * Every piece of interface text, in all supported languages.
 *
 * Each row holds the translations in [Language.entries] order, so adding a
 * language means adding one entry to the enum and one string per row.
 */
private val TABLE: Map<String, List<String>> = mapOf(
    // ---------------------------------------------------------------- home
    "hero_badge" to listOf("✦ Premium Insights", "✦ پریمیم معلومات", "✦ رؤى متميزة", "✦ प्रीमियम जानकारी", "✦ بینش ویژه", "✦ Información premium"),
    "hero_title" to listOf("About You", "آپ کے بارے میں", "عنك", "आपके बारे में", "درباره تو", "Sobre ti"),
    "hero_subtitle" to listOf(
        "The meaning of your name, your age to the second, your star sign and everyone who shares your birthday.",
        "آپ کے نام کا مطلب، سیکنڈ تک آپ کی عمر، آپ کا ستارہ اور وہ سب جو آپ کے ساتھ سالگرہ مناتے ہیں۔",
        "معنى اسمك، عمرك بالثانية، برجك، وكل من يشاركك عيد ميلادك.",
        "आपके नाम का अर्थ, सेकंड तक आपकी आयु, आपकी राशि और आपके जन्मदिन पर जन्मे सभी लोग।",
        "معنی نام تو، سن تو تا ثانیه، برج تو و همه کسانی که تولدشان با تو یکی است.",
        "El significado de tu nombre, tu edad al segundo, tu signo y todos los que comparten tu cumpleaños."
    ),
    "step_name" to listOf("1. Write your name", "۱۔ اپنا نام لکھیں", "١. اكتب اسمك", "१. अपना नाम लिखें", "۱. نامت را بنویس", "1. Escribe tu nombre"),
    "step_date" to listOf("2. Type your date of birth", "۲۔ اپنی تاریخِ پیدائش لکھیں", "٢. اكتب تاريخ ميلادك", "२. अपनी जन्मतिथि लिखें", "۲. تاریخ تولدت را بنویس", "2. Escribe tu fecha de nacimiento"),
    "step_language" to listOf("3. Choose the result language", "۳۔ نتیجے کی زبان چنیں", "٣. اختر لغة النتيجة", "३. परिणाम की भाषा चुनें", "۳. زبان نتیجه را انتخاب کن", "3. Elige el idioma del resultado"),
    "name_label" to listOf("Your name", "آپ کا نام", "اسمك", "आपका नाम", "نام تو", "Tu nombre"),
    "name_hint" to listOf("e.g. Muhammad Ali", "مثلاً محمد علی", "مثال: محمد", "जैसे: मोहन", "مثلاً: علی", "p. ej. Muhammad Ali"),
    "dob_label" to listOf("Date of birth", "تاریخِ پیدائش", "تاريخ الميلاد", "जन्म तिथि", "تاریخ تولد", "Fecha de nacimiento"),
    "dob_hint" to listOf("Type it — no calendar needed", "ٹائپ کریں — کیلنڈر کی ضرورت نہیں", "اكتبها — لا حاجة للتقويم", "टाइप करें — कैलेंडर की ज़रूरत नहीं", "تایپ کن — نیازی به تقویم نیست", "Escríbela — sin calendario"),
    "f_day" to listOf("Day", "دن", "يوم", "दिन", "روز", "Día"),
    "f_month" to listOf("Month", "مہینہ", "شهر", "महीना", "ماه", "Mes"),
    "f_year" to listOf("Year", "سال", "سنة", "वर्ष", "سال", "Año"),
    "f_hour" to listOf("Hour", "گھنٹہ", "ساعة", "घंटा", "ساعت", "Hora"),
    "f_minute" to listOf("Minute", "منٹ", "دقيقة", "मिनट", "دقیقه", "Minuto"),
    "optional_time" to listOf("Time of birth (optional)", "پیدائش کا وقت (اختیاری)", "وقت الميلاد (اختياري)", "जन्म का समय (वैकल्पिक)", "ساعت تولد (اختیاری)", "Hora de nacimiento (opcional)"),
    "language_label" to listOf("Result language", "نتیجے کی زبان", "لغة النتيجة", "परिणाम भाषा", "زبان نتیجه", "Idioma del resultado"),
    "analyze" to listOf("Analyze", "تجزیہ کریں", "تحليل", "विश्लेषण करें", "تحلیل کن", "Analizar"),
    "analyze_hint" to listOf("Name and date of birth are both needed", "نام اور تاریخِ پیدائش دونوں ضروری ہیں", "الاسم وتاريخ الميلاد مطلوبان", "नाम और जन्मतिथि दोनों ज़रूरी हैं", "نام و تاریخ تولد هر دو لازم است", "Se necesitan el nombre y la fecha"),

    // ---------------------------------------------------------------- tabs
    "tab_home" to listOf("Home", "ہوم", "الرئيسية", "होम", "خانه", "Inicio"),
    "tab_name" to listOf("Name", "نام", "الاسم", "नाम", "نام", "Nombre"),
    "tab_birthday" to listOf("Birthday", "سالگرہ", "الميلاد", "जन्मदिन", "تولد", "Cumpleaños"),
    "tab_settings" to listOf("Settings", "سیٹنگز", "الإعدادات", "सेटिंग्स", "تنظیمات", "Ajustes"),

    // ---------------------------------------------------------------- name screen
    "name_title" to listOf("Name Insights", "نام کی معلومات", "أسرار الاسم", "नाम की जानकारी", "رازهای نام", "Detalles del nombre"),
    "name_empty" to listOf(
        "Write your name on the Home screen, then tap Analyze.",
        "ہوم اسکرین پر اپنا نام لکھیں، پھر تجزیہ کریں دبائیں۔",
        "اكتب اسمك في الرئيسية ثم اضغط تحليل.",
        "होम पर अपना नाम लिखें, फिर विश्लेषण दबाएँ।",
        "نامت را در خانه بنویس، سپس تحلیل کن را بزن.",
        "Escribe tu nombre en Inicio y pulsa Analizar."
    ),
    "meaning" to listOf("Meaning", "مطلب", "المعنى", "अर्थ", "معنی", "Significado"),
    "name_profile" to listOf("Name Profile", "نام کا خاکہ", "ملف الاسم", "नाम प्रोफ़ाइल", "نمایه نام", "Perfil del nombre"),
    "origin" to listOf("Origin", "اصل", "الأصل", "मूल", "ریشه", "Origen"),
    "culture" to listOf("Culture", "ثقافت", "الثقافة", "संस्कृति", "فرهنگ", "Cultura"),
    "gender" to listOf("Gender", "جنس", "الجنس", "लिंग", "جنسیت", "Género"),
    "lucky_number" to listOf("Lucky number", "مبارک عدد", "الرقم المحظوظ", "शुभ अंक", "عدد خوش‌یمن", "Número de la suerte"),
    "lucky_colour" to listOf("Lucky colour", "مبارک رنگ", "اللون المحظوظ", "शुभ रंग", "رنگ خوش‌یمن", "Color de la suerte"),
    "confidence" to listOf("Data confidence", "ڈیٹا کا اعتماد", "ثقة البيانات", "डेटा विश्वास", "اطمینان داده", "Confianza del dato"),
    "traits" to listOf("Personality Traits", "ذاتی خصوصیات", "سمات الشخصية", "व्यक्तित्व लक्षण", "ویژگی‌های شخصیتی", "Rasgos de personalidad"),
    "live_stats" to listOf("Live Name Statistics", "نام کے لائیو اعدادوشمار", "إحصاءات الاسم", "नाम के आँकड़े", "آمار زنده نام", "Estadísticas del nombre"),
    "mostly_used" to listOf("Mostly used as", "زیادہ تر استعمال", "يُستخدم غالبًا كـ", "मुख्य रूप से", "بیشتر به‌عنوان", "Se usa sobre todo como"),
    "most_common_in" to listOf("Most common in", "سب سے زیادہ کہاں", "الأكثر شيوعًا في", "सर्वाधिक प्रचलित", "رایج‌ترین در", "Más común en"),
    "encyclopaedia" to listOf("From the Encyclopaedia", "انسائیکلوپیڈیا سے", "من الموسوعة", "विश्वकोश से", "از دانشنامه", "De la enciclopedia"),
    "famous_names" to listOf("Famous People With This Name", "اس نام کے مشہور لوگ", "مشاهير بهذا الاسم", "इस नाम के प्रसिद्ध लोग", "افراد مشهور با این نام", "Famosos con este nombre"),
    "none_found" to listOf("Nothing was found for this entry.", "اس کے لیے کچھ نہیں ملا۔", "لم يتم العثور على شيء.", "इसके लिए कुछ नहीं मिला।", "چیزی یافت نشد.", "No se encontró nada."),
    "name_boy" to listOf("Boy", "لڑکے", "ذكر", "लड़का", "پسر", "Niño"),
    "name_girl" to listOf("Girl", "لڑکی", "أنثى", "लड़की", "دختر", "Niña"),
    "name_both" to listOf("Unisex", "دونوں", "للجنسين", "उभयलिंगी", "دوجنسیتی", "Unisex"),

    // ---------------------------------------------------------------- birthday screen
    "birth_title" to listOf("Your Birthday", "آپ کی سالگرہ", "عيد ميلادك", "आपका जन्मदिन", "تولد تو", "Tu cumpleaños"),
    "born_on" to listOf("Born on", "پیدائش", "وُلد في", "जन्म", "متولد", "Nacido el"),
    "at_time" to listOf("at", "بوقت", "في", "को", "در ساعت", "a las"),
    "live_counter" to listOf("Live Counter", "لائیو کاؤنٹر", "عدّاد مباشر", "लाइव काउंटर", "شمارنده زنده", "Contador en vivo"),
    "live_sub" to listOf("updated every second", "ہر سیکنڈ اپڈیٹ", "يُحدَّث كل ثانية", "हर सेकंड अपडेट", "هر ثانیه به‌روز", "actualizado cada segundo"),
    "years" to listOf("Years", "سال", "سنوات", "वर्ष", "سال", "Años"),
    "months" to listOf("Months", "مہینے", "أشهر", "महीने", "ماه", "Meses"),
    "days" to listOf("Days", "دن", "أيام", "दिन", "روز", "Días"),
    "hours" to listOf("Hours", "گھنٹے", "ساعات", "घंटे", "ساعت", "Horas"),
    "minutes" to listOf("Minutes", "منٹ", "دقائق", "मिनट", "دقیقه", "Minutos"),
    "seconds" to listOf("Seconds", "سیکنڈ", "ثوانٍ", "सेकंड", "ثانیه", "Segundos"),
    "total_time" to listOf("Total Time", "کل وقت", "الوقت الكلي", "कुल समय", "زمان کل", "Tiempo total"),
    "total_months" to listOf("Total months", "کل مہینے", "إجمالي الأشهر", "कुल महीने", "کل ماه", "Meses totales"),
    "total_weeks" to listOf("Total weeks", "کل ہفتے", "إجمالي الأسابيع", "कुल सप्ताह", "کل هفته", "Semanas totales"),
    "total_days" to listOf("Total days", "کل دن", "إجمالي الأيام", "कुल दिन", "کل روز", "Días totales"),
    "total_hours" to listOf("Total hours", "کل گھنٹے", "إجمالي الساعات", "कुल घंटे", "کل ساعت", "Horas totales"),
    "total_minutes" to listOf("Total minutes", "کل منٹ", "إجمالي الدقائق", "कुल मिनट", "کل دقیقه", "Minutos totales"),
    "total_seconds" to listOf("Total seconds", "کل سیکنڈ", "إجمالي الثواني", "कुल सेकंड", "کل ثانیه", "Segundos totales"),
    "next_birthday" to listOf("Next Birthday", "اگلی سالگرہ", "عيد الميلاد القادم", "अगला जन्मदिन", "تولد بعدی", "Próximo cumpleaños"),
    "days_to_go" to listOf("Days to go", "دن باقی", "الأيام المتبقية", "शेष दिन", "روز مانده", "Días restantes"),
    "turning" to listOf("Turning", "ہو جائیں گے", "ستبلغ", "होंगे", "خواهی شد", "Cumplirás"),
    "star_sign" to listOf("Your Star Sign", "آپ کا ستارہ", "برجك", "आपकी राशि", "برج تو", "Tu signo"),
    "element" to listOf("Element", "عنصر", "العنصر", "तत्व", "عنصر", "Elemento"),
    "quality" to listOf("Quality", "کیفیت", "الطبيعة", "गुण", "کیفیت", "Cualidad"),
    "ruling_planet" to listOf("Ruling planet", "حاکم سیارہ", "الكوكب الحاكم", "स्वामी ग्रह", "سیاره حاکم", "Planeta regente"),
    "lucky_numbers" to listOf("Lucky numbers", "مبارک اعداد", "الأرقام المحظوظة", "शुभ अंक", "اعداد خوش‌یمن", "Números de la suerte"),
    "best_match" to listOf("Best match", "بہترین جوڑ", "أفضل توافق", "सर्वोत्तम मेल", "بهترین هماهنگی", "Mejor afinidad"),
    "strengths" to listOf("Strengths", "خوبیاں", "نقاط القوة", "शक्तियाँ", "نقاط قوت", "Fortalezas"),
    "watch_outs" to listOf("Watch-outs", "احتیاطیں", "نقاط الضعف", "सावधानियाँ", "نقاط ضعف", "A tener en cuenta"),
    "exact_date" to listOf("On Your Exact Date", "آپ کی عین تاریخ", "في تاريخك بالتحديد", "आपकी ठीक तारीख", "در تاریخ دقیق تو", "En tu fecha exacta"),
    "famous_day" to listOf("Famous Birthdays On This Day", "اس دن کی مشہور سالگرہیں", "مشاهير وُلدوا في هذا اليوم", "इस दिन के प्रसिद्ध जन्मदिन", "تولدهای مشهور این روز", "Cumpleaños famosos de este día"),
    "every_year" to listOf("Every year • from the world feed", "ہر سال • عالمی فیڈ سے", "كل عام • من التغذية العالمية", "हर वर्ष • विश्व फ़ीड से", "هر سال • از خوراک جهانی", "Cada año • del feed mundial"),
    "sharing" to listOf("people share your exact birthday", "لوگوں کی عین وہی تاریخ ہے", "شخصًا يشاركونك تاريخك", "लोगों की वही तारीख है", "نفر همین تاریخ را دارند", "personas comparten tu fecha"),
    "born_year" to listOf("Born", "پیدائش", "مولود", "जन्म", "متولد", "Nacido"),
    "searching_wikidata" to listOf("Searching Wikidata for this exact day…", "ویکی ڈیٹا میں اس دن کو تلاش کیا جا رہا ہے…", "جارٍ البحث في ويكي بيانات…", "विकिडेटा में खोज रहे हैं…", "جست‌وجو در ویکی‌داده…", "Buscando en Wikidata…"),
    "collecting" to listOf("Collecting birthdays from around the world…", "دنیا بھر سے سالگرہ جمع کی جا رہی ہیں…", "جارٍ جمع أعياد الميلاد…", "दुनिया भर से जन्मदिन जुटा रहे हैं…", "گردآوری تولدها از سراسر جهان…", "Recopilando cumpleaños del mundo…"),
    "no_notable" to listOf("No notable births were found for this exact date.", "اس عین تاریخ پر کوئی مشہور پیدائش نہیں ملی۔", "لم يُعثر على مواليد مشهورين.", "इस तारीख पर कोई प्रसिद्ध जन्म नहीं मिला।", "تولد مشهوری یافت نشد.", "No hay nacimientos destacados."),
    "enter_dob_first" to listOf("Enter your date of birth to begin.", "شروع کرنے کے لیے تاریخِ پیدائش درج کریں۔", "أدخل تاريخ ميلادك للبدء.", "शुरू करने के लिए जन्मतिथि दर्ज करें।", "برای شروع تاریخ تولدت را وارد کن.", "Introduce tu fecha para empezar."),
    "searching_name" to listOf("Searching the whole internet for this name…", "پورے انٹرنیٹ پر یہ نام تلاش کیا جا رہا ہے…", "جارٍ البحث في الإنترنت…", "पूरे इंटरनेट पर खोज रहे हैं…", "جست‌وجو در سراسر اینترنت…", "Buscando en todo internet…"),
    "loading" to listOf("Fetching live data…", "لائیو ڈیٹا لایا جا رہا ہے…", "جارٍ جلب البيانات…", "लाइव डेटा ला रहे हैं…", "دریافت داده زنده…", "Obteniendo datos…"),
    "error_net" to listOf("Could not reach the internet. Check your connection and try again.", "انٹرنیٹ سے رابطہ نہیں ہو سکا۔ اپنا کنکشن دیکھیں اور دوبارہ کوشش کریں۔", "تعذّر الاتصال بالإنترنت.", "इंटरनेट से संपर्क नहीं हुआ।", "اتصال به اینترنت برقرار نشد.", "No se pudo conectar a internet."),

    // ---------------------------------------------------------------- settings
    "set_appearance" to listOf("Appearance", "ظاہری شکل", "المظهر", "रूप-रंग", "ظاهر", "Apariencia"),
    "set_language" to listOf("Language", "زبان", "اللغة", "भाषा", "زبان", "Idioma"),
    "set_language_sub" to listOf("Results are fetched in this language", "نتائج اسی زبان میں آئیں گے", "تُجلب النتائج بهذه اللغة", "परिणाम इसी भाषा में आएँगे", "نتایج به این زبان می‌آیند", "Los resultados llegan en este idioma"),
    "set_notifications" to listOf("Notifications", "اطلاعات", "الإشعارات", "सूचनाएँ", "اعلان‌ها", "Notificaciones"),
    "set_birthday_reminder" to listOf("Birthday reminder", "سالگرہ کی یاد دہانی", "تذكير عيد الميلاد", "जन्मदिन अनुस्मारक", "یادآور تولد", "Recordatorio de cumpleaños"),
    "set_birthday_reminder_sub" to listOf("Remind me a day before", "ایک دن پہلے یاد دہانی", "تذكيري قبل يوم", "एक दिन पहले याद दिलाएँ", "یک روز قبل یادآوری", "Avisarme un día antes"),
    "set_daily_insight" to listOf("Daily insight", "روزانہ کی معلومات", "معلومة يومية", "दैनिक जानकारी", "بینش روزانه", "Dato diario"),
    "set_daily_insight_sub" to listOf("A short thought every morning", "ہر صبح ایک مختصر بات", "فكرة كل صباح", "हर सुबह एक बात", "هر صبح یک نکته", "Una idea cada mañana"),
    "set_monetisation" to listOf("Monetisation", "آمدنی", "الربح", "मुद्रीकरण", "درآمد", "Monetización"),
    "set_ads" to listOf("Show ads", "اشتہارات دکھائیں", "عرض الإعلانات", "विज्ञापन दिखाएँ", "نمایش تبلیغات", "Mostrar anuncios"),
    "set_ads_sub" to listOf("Reserved ad space is already laid out", "اشتہار کی جگہ پہلے سے مختص ہے", "مساحة الإعلان محجوزة", "विज्ञापन स्थान आरक्षित है", "فضای تبلیغ آماده است", "El espacio ya está reservado"),
    "set_ads_off_note" to listOf("Ads are switched off in this build. The layout already reserves the banner space, so turning them on later needs no redesign.", "اس بلڈ میں اشتہار بند ہیں۔ لے آؤٹ میں بینر کی جگہ پہلے سے مختص ہے، اس لیے بعد میں چالو کرنے پر ڈیزائن بدلنے کی ضرورت نہیں۔", "الإعلانات معطّلة في هذا الإصدار.", "इस बिल्ड में विज्ञापन बंद हैं।", "تبلیغات در این نسخه خاموش است.", "Los anuncios están desactivados en esta versión."),
    "set_content" to listOf("Content", "مواد", "المحتوى", "सामग्री", "محتوا", "Contenido"),
    "set_sources" to listOf("Data sources", "ڈیٹا کے ذرائع", "مصادر البيانات", "डेटा स्रोत", "منابع داده", "Fuentes de datos"),
    "set_sources_sub" to listOf("Wikimedia, Wikidata, Wikipedia, Commons", "وکیمیڈیا، ویکی ڈیٹا، ویکیپیڈیا، کامنز", "ويكيميديا، ويكي بيانات، ويكيبيديا", "विकिमीडिया, विकिडेटा, विकिपीडिया", "ویکی‌مدیا، ویکی‌داده، ویکی‌پدیا", "Wikimedia, Wikidata, Wikipedia, Commons"),
    "set_print" to listOf("Print & share", "پرنٹ اور شیئر", "الطباعة والمشاركة", "प्रिंट और साझा करें", "چاپ و اشتراک", "Imprimir y compartir"),
    "set_print_sub" to listOf("Save your full report as text", "اپنی مکمل رپورٹ متن میں محفوظ کریں", "احفظ تقريرك كنص", "अपनी रिपोर्ट टेक्स्ट में सहेजें", "گزارش را به‌صورت متن ذخیره کن", "Guarda tu informe como texto"),
    "set_share" to listOf("Share my report", "میری رپورٹ شیئر کریں", "شارك تقريري", "मेरी रिपोर्ट साझा करें", "اشتراک گزارش من", "Compartir mi informe"),
    "set_copy" to listOf("Copy my report", "میری رپورٹ کاپی کریں", "انسخ تقريري", "मेरी रिपोर्ट कॉपी करें", "رونوشت گزارش من", "Copiar mi informe"),
    "set_about" to listOf("About", "تعارف", "حول", "परिचय", "درباره", "Acerca de"),
    "set_version" to listOf("Version", "ورژن", "الإصدار", "संस्करण", "نسخه", "Versión"),
    "set_clear" to listOf("Clear my data", "میرا ڈیٹا صاف کریں", "امسح بياناتي", "मेरा डेटा हटाएँ", "پاک کردن داده من", "Borrar mis datos"),
    "set_cleared" to listOf("Your saved data was cleared.", "آپ کا محفوظ ڈیٹا صاف کر دیا گیا۔", "تم مسح بياناتك.", "आपका डेटा हटा दिया गया।", "داده‌ات پاک شد.", "Tus datos se borraron."),
    "yes" to listOf("On", "آن", "مفعّل", "चालू", "روشن", "Sí"),
    "no" to listOf("Off", "آف", "معطّل", "बंद", "خاموش", "No"),
    "share_via" to listOf("Share your report", "اپنی رپورٹ شیئر کریں", "شارك تقريرك", "अपनी रिपोर्ट साझा करें", "گزارشت را به اشتراک بگذار", "Comparte tu informe"),

    // ---------------------------------------------------------------- misc
    "ad_placeholder" to listOf("Ad space (reserved)", "اشتہار کی جگہ (مختص)", "مساحة إعلان (محجوزة)", "विज्ञापन स्थान (आरक्षित)", "فضای تبلیغ (رزرو)", "Espacio publicitario"),
    "powered_by" to listOf(
        "Live data from Wikimedia, Wikidata and Wikipedia — searched across the whole internet.",
        "لائیو ڈیٹا وکیمیڈیا، ویکی ڈیٹا اور ویکیپیڈیا سے — پورے انٹرنیٹ پر تلاش کے بعد۔",
        "بيانات مباشرة من ويكيميديا وويكي بيانات وويكيبيديا.",
        "विकिमीडिया, विकिडेटा और विकिपीडिया से लाइव डेटा।",
        "داده زنده از ویکی‌مدیا، ویکی‌داده و ویکی‌پدیا.",
        "Datos en vivo de Wikimedia, Wikidata y Wikipedia."
    )
)

/**
 * Typed access to the interface text for one [Language].
 *
 * Missing translations fall back to the key, so a half-filled table still
 * renders something readable instead of crashing.
 */
class Strings(val language: Language) {

    private fun t(key: String): String {
        val row = TABLE[key] ?: return key
        return row.getOrNull(language.ordinal)?.takeIf { it.isNotBlank() }
            ?: row.firstOrNull()
            ?: key
    }

    val heroBadge get() = t("hero_badge")
    val heroTitle get() = t("hero_title")
    val heroSubtitle get() = t("hero_subtitle")
    val stepName get() = t("step_name")
    val stepDate get() = t("step_date")
    val stepLanguage get() = t("step_language")
    val nameLabel get() = t("name_label")
    val nameHint get() = t("name_hint")
    val dobLabel get() = t("dob_label")
    val dobHint get() = t("dob_hint")
    val fDay get() = t("f_day")
    val fMonth get() = t("f_month")
    val fYear get() = t("f_year")
    val fHour get() = t("f_hour")
    val fMinute get() = t("f_minute")
    val optionalTime get() = t("optional_time")
    val languageLabel get() = t("language_label")
    val analyze get() = t("analyze")
    val analyzeHint get() = t("analyze_hint")

    val tabHome get() = t("tab_home")
    val tabName get() = t("tab_name")
    val tabBirthday get() = t("tab_birthday")
    val tabSettings get() = t("tab_settings")

    val nameTitle get() = t("name_title")
    val nameEmpty get() = t("name_empty")
    val meaning get() = t("meaning")
    val nameProfile get() = t("name_profile")
    val origin get() = t("origin")
    val culture get() = t("culture")
    val gender get() = t("gender")
    val luckyNumber get() = t("lucky_number")
    val luckyColour get() = t("lucky_colour")
    val confidence get() = t("confidence")
    val traits get() = t("traits")
    val liveStats get() = t("live_stats")
    val mostlyUsed get() = t("mostly_used")
    val mostCommonIn get() = t("most_common_in")
    val encyclopaedia get() = t("encyclopaedia")
    val famousNames get() = t("famous_names")
    val noneFound get() = t("none_found")
    val nameBoy get() = t("name_boy")
    val nameGirl get() = t("name_girl")
    val nameBoth get() = t("name_both")

    val birthTitle get() = t("birth_title")
    val bornOn get() = t("born_on")
    val atTime get() = t("at_time")
    val liveCounter get() = t("live_counter")
    val liveSub get() = t("live_sub")
    val years get() = t("years")
    val months get() = t("months")
    val days get() = t("days")
    val hours get() = t("hours")
    val minutes get() = t("minutes")
    val seconds get() = t("seconds")
    val totalTime get() = t("total_time")
    val totalMonths get() = t("total_months")
    val totalWeeks get() = t("total_weeks")
    val totalDays get() = t("total_days")
    val totalHours get() = t("total_hours")
    val totalMinutes get() = t("total_minutes")
    val totalSeconds get() = t("total_seconds")
    val nextBirthday get() = t("next_birthday")
    val daysToGo get() = t("days_to_go")
    val turning get() = t("turning")
    val starSign get() = t("star_sign")
    val element get() = t("element")
    val quality get() = t("quality")
    val rulingPlanet get() = t("ruling_planet")
    val luckyNumbers get() = t("lucky_numbers")
    val bestMatch get() = t("best_match")
    val strengths get() = t("strengths")
    val watchOuts get() = t("watch_outs")
    val exactDate get() = t("exact_date")
    val famousDay get() = t("famous_day")
    val everyYear get() = t("every_year")
    val sharing get() = t("sharing")
    val bornYear get() = t("born_year")
    val searchingWikidata get() = t("searching_wikidata")
    val collecting get() = t("collecting")
    val noNotable get() = t("no_notable")
    val enterDobFirst get() = t("enter_dob_first")
    val searchingName get() = t("searching_name")
    val loading get() = t("loading")
    val errorNet get() = t("error_net")

    val setAppearance get() = t("set_appearance")
    val setLanguage get() = t("set_language")
    val setLanguageSub get() = t("set_language_sub")
    val setNotifications get() = t("set_notifications")
    val setBirthdayReminder get() = t("set_birthday_reminder")
    val setBirthdayReminderSub get() = t("set_birthday_reminder_sub")
    val setDailyInsight get() = t("set_daily_insight")
    val setDailyInsightSub get() = t("set_daily_insight_sub")
    val setMonetisation get() = t("set_monetisation")
    val setAds get() = t("set_ads")
    val setAdsSub get() = t("set_ads_sub")
    val setAdsOffNote get() = t("set_ads_off_note")
    val setContent get() = t("set_content")
    val setSources get() = t("set_sources")
    val setSourcesSub get() = t("set_sources_sub")
    val setPrint get() = t("set_print")
    val setPrintSub get() = t("set_print_sub")
    val setShare get() = t("set_share")
    val setCopy get() = t("set_copy")
    val setAbout get() = t("set_about")
    val setVersion get() = t("set_version")
    val setClear get() = t("set_clear")
    val setCleared get() = t("set_cleared")
    val yes get() = t("yes")
    val no get() = t("no")
    val shareVia get() = t("share_via")

    val adPlaceholder get() = t("ad_placeholder")
    val poweredBy get() = t("powered_by")
}
