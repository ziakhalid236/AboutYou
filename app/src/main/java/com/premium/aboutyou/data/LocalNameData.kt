package com.premium.aboutyou.data

/**
 * Curated offline name meanings. The app first checks this list, then
 * enriches the result with live data from Wikipedia / public name APIs.
 */
object LocalNameData {

    private val names: Map<String, NameMeaning> = listOf(
        NameMeaning("muhammad", "The Praised One; highly praised and praiseworthy", "بہت تعریف کیا گیا، قابلِ ستائش", "Arabic", "Islamic / Arabic", "Male", "5", "Green", listOf("Honest", "Leader", "Compassionate", "Respected")),
        NameMeaning("ahmed", "Most praised; one who praises God frequently", "سب سے زیادہ تعریف کرنے والا", "Arabic", "Islamic / Arabic", "Male", "8", "Green", listOf("Devout", "Modest", "Sincere")),
        NameMeaning("ali", "Exalted, noble, high in rank", "بلند، عالی مرتبہ", "Arabic", "Islamic / Arabic", "Male", "2", "Red", listOf("Brave", "Just", "Wise")),
        NameMeaning("fatima", "One who abstains; the Prophet's daughter", "پاکیزہ، پرہیزگار", "Arabic", "Islamic / Arabic", "Female", "7", "White", listOf("Gentle", "Devoted", "Patient")),
        NameMeaning("aisha", "Alive, living, prosperous", "زندہ، خوش حال", "Arabic", "Islamic / Arabic", "Female", "6", "Green", listOf("Lively", "Intelligent", "Kind")),
        NameMeaning("hassan", "Beautiful, handsome, good", "خوبصورت، اچھا", "Arabic", "Islamic / Arabic", "Male", "4", "Blue", listOf("Charming", "Eloquent", "Calm")),
        NameMeaning("hussain", "Beautiful, good; diminutive of Hassan", "خوبصورت، نیک", "Arabic", "Islamic / Arabic", "Male", "9", "Blue", listOf("Courageous", "Faithful", "Generous")),
        NameMeaning("usman", "Young bustard; companion of the Prophet", "نوجوان، صحابیِ رسول", "Arabic", "Islamic / Arabic", "Male", "1", "Green", listOf("Generous", "Modest", "Truthful")),
        NameMeaning("umar", "Flourishing, long-lived, prosperous", "آباد، طویل العمر", "Arabic", "Islamic / Arabic", "Male", "3", "Red", listOf("Decisive", "Strong", "Fair")),
        NameMeaning("abdullah", "Servant of God", "اللہ کا بندہ", "Arabic", "Islamic / Arabic", "Male", "4", "Green", listOf("Humble", "Loyal", "Pious")),
        NameMeaning("maryam", "Beloved, exalted; mother of Jesus", "محبوب، بلند مرتبہ", "Hebrew / Arabic", "Abrahamic", "Female", "3", "White", listOf("Pure", "Patient", "Graceful")),
        NameMeaning("zainab", "Fragrant flower; daughter of the Prophet", "خوشبودار پھول", "Arabic", "Islamic / Arabic", "Female", "5", "Yellow", listOf("Graceful", "Dignified", "Caring")),
        NameMeaning("bilal", "Moisture, freshness; first muezzin of Islam", "تازگی، اسلام کے پہلے مؤذن", "Arabic", "Islamic / Arabic", "Male", "4", "Green", listOf("Steadfast", "Sweet-voiced", "Loyal")),
        NameMeaning("hamza", "Strong, steadfast, lion", "مضبوط، بہادر شیر", "Arabic", "Islamic / Arabic", "Male", "6", "Red", listOf("Brave", "Strong", "Protective")),
        NameMeaning("sara", "Princess, noble lady", "شہزادی، اعلیٰ خاتون", "Hebrew", "Abrahamic", "Female", "2", "Purple", listOf("Noble", "Wise", "Independent")),
        NameMeaning("adam", "Man, of the red earth", "انسان، مٹی سے بنا", "Hebrew", "Abrahamic", "Male", "1", "Red", listOf("Pioneering", "Curious", "Strong")),
        NameMeaning("noah", "Rest, comfort, long-lived", "سکون، آرام، طویل العمر", "Hebrew", "Abrahamic", "Male", "7", "Blue", listOf("Patient", "Steady", "Righteous")),
        NameMeaning("daniel", "God is my judge", "خدا میرا منصف ہے", "Hebrew", "Biblical", "Male", "6", "Blue", listOf("Wise", "Fair", "Insightful")),
        NameMeaning("david", "Beloved", "محبوب", "Hebrew", "Biblical", "Male", "5", "Gold", listOf("Charismatic", "Courageous", "Artistic")),
        NameMeaning("joseph", "God will increase", "خدا اضافہ کرے گا", "Hebrew", "Biblical", "Male", "8", "Green", listOf("Reliable", "Forgiving", "Diligent")),
        NameMeaning("john", "God is gracious", "خدا مہربان ہے", "Hebrew", "Biblical", "Male", "3", "Blue", listOf("Kind", "Honest", "Steady")),
        NameMeaning("james", "Supplanter, one who follows", "جانشین، پیروی کرنے والا", "Hebrew", "Biblical", "Male", "1", "Blue", listOf("Determined", "Loyal", "Practical")),
        NameMeaning("michael", "Who is like God?", "کون خدا جیسا ہے؟", "Hebrew", "Biblical", "Male", "4", "Blue", listOf("Protective", "Brave", "Faithful")),
        NameMeaning("william", "Resolute protector", "ثابت قدم محافظ", "Germanic", "English", "Male", "7", "Blue", listOf("Loyal", "Resolute", "Noble")),
        NameMeaning("emma", "Whole, universal, entire", "مکمل، عالمگیر", "Germanic", "English", "Female", "5", "White", listOf("Caring", "Creative", "Balanced")),
        NameMeaning("olivia", "Olive tree; peace", "زیتون کا درخت، امن", "Latin", "English", "Female", "6", "Green", listOf("Peaceful", "Elegant", "Warm")),
        NameMeaning("sophia", "Wisdom", "حکمت، دانش", "Greek", "European", "Female", "7", "Violet", listOf("Wise", "Graceful", "Insightful")),
        NameMeaning("mia", "Mine; beloved; bitter", "میرا، محبوب", "Italian", "European", "Female", "1", "Pink", listOf("Spirited", "Affectionate", "Bold")),
        NameMeaning("isabella", "Devoted to God", "خدا سے وابستہ", "Hebrew / Italian", "European", "Female", "9", "Purple", listOf("Devoted", "Elegant", "Strong")),
        NameMeaning("lucas", "Bringer of light", "روشنی لانے والا", "Latin", "European", "Male", "3", "Gold", listOf("Bright", "Optimistic", "Social")),
        NameMeaning("alexander", "Defender of the people", "لوگوں کا محافظ", "Greek", "European", "Male", "5", "Blue", listOf("Bold", "Charismatic", "Strategic")),
        NameMeaning("ryan", "Little king", "چھوٹا بادشاہ", "Irish", "European", "Male", "6", "Green", listOf("Energetic", "Charming", "Determined")),
        NameMeaning("priya", "Beloved, dear one", "پیاری، محبوب", "Sanskrit", "South Asian", "Female", "4", "Pink", listOf("Affectionate", "Gentle", "Loyal")),
        NameMeaning("aarav", "Peaceful, calm, melodious", "پرسکون، خوش آواز", "Sanskrit", "South Asian", "Male", "2", "Blue", listOf("Calm", "Thoughtful", "Kind")),
        NameMeaning("aditya", "The sun, son of Aditi", "سورج، ادیتی کا بیٹا", "Sanskrit", "South Asian", "Male", "1", "Orange", listOf("Radiant", "Confident", "Warm")),
        NameMeaning("ananya", "Unique, without equal", "یکتا، بے مثال", "Sanskrit", "South Asian", "Female", "3", "Violet", listOf("Unique", "Creative", "Graceful")),
        NameMeaning("ravi", "The sun", "سورج", "Sanskrit", "South Asian", "Male", "1", "Orange", listOf("Bright", "Decisive", "Protective")),
        NameMeaning("chen", "Dawn, morning; great", "صبح، طلوع", "Chinese", "East Asian", "Unisex", "8", "Red", listOf("Ambitious", "Disciplined", "Insightful")),
        NameMeaning("yuki", "Snow; happiness", "برف، خوشی", "Japanese", "East Asian", "Unisex", "5", "White", listOf("Pure", "Graceful", "Calm")),
        NameMeaning("haruto", "Soaring sun, sun flying high", "بلند اڑتا سورج", "Japanese", "East Asian", "Male", "6", "Orange", listOf("Ambitious", "Bright", "Free")),
        NameMeaning("sakura", "Cherry blossom", "چیری کا پھول", "Japanese", "East Asian", "Female", "3", "Pink", listOf("Gentle", "Artistic", "Renewing")),
        NameMeaning("noor", "Light, radiance", "روشنی، نور", "Arabic", "Islamic / Arabic", "Female", "5", "Gold", listOf("Radiant", "Kind", "Serene")),
        NameMeaning("amina", "Trustworthy, faithful", "امانت دار، قابلِ اعتماد", "Arabic", "Islamic / Arabic", "Female", "4", "Green", listOf("Trustworthy", "Calm", "Wise")),
        NameMeaning("ibrahim", "Father of many; exalted father", "کثیر قوموں کا باپ", "Hebrew / Arabic", "Abrahamic", "Male", "1", "Blue", listOf("Visionary", "Faithful", "Generous")),
        NameMeaning("yusuf", "God will increase", "خدا اضافہ کرے گا", "Hebrew / Arabic", "Abrahamic", "Male", "8", "Green", listOf("Handsome", "Patient", "Forgiving")),
        NameMeaning("moses", "Drawn out of the water", "پانی سے نکالا گیا", "Hebrew", "Abrahamic", "Male", "7", "Blue", listOf("Liberator", "Humble", "Determined")),
        NameMeaning("jesus", "The Lord is salvation", "خداوند نجات ہے", "Hebrew", "Abrahamic", "Male", "3", "Gold", listOf("Compassionate", "Humble", "Healing")),
        NameMeaning("linda", "Beautiful, tender", "خوبصورت، نرم", "Spanish / Germanic", "European", "Female", "4", "Pink", listOf("Gentle", "Warm", "Considerate")),
        NameMeaning("victoria", "Victory", "فتح، کامیابی", "Latin", "European", "Female", "7", "Purple", listOf("Triumphant", "Strong", "Confident")),
        NameMeaning("victor", "Conqueror, winner", "فاتح، جیتنے والا", "Latin", "European", "Male", "6", "Red", listOf("Winning", "Bold", "Driven")),
        NameMeaning("grace", "Elegance, divine favor", "لطف، خوبصورتی", "Latin", "English", "Female", "3", "White", listOf("Elegant", "Kind", "Graceful")),
        NameMeaning("henry", "Ruler of the home", "گھر کا حکمران", "Germanic", "English", "Male", "2", "Blue", listOf("Steady", "Noble", "Responsible")),
        NameMeaning("charlotte", "Free woman, petite", "آزاد خاتون", "Germanic / French", "European", "Female", "5", "Pink", listOf("Free", "Refined", "Resilient")),
        NameMeaning("amelia", "Industrious, striving", "محنتی، کوشاں", "Germanic", "English", "Female", "6", "Blue", listOf("Hardworking", "Warm", "Capable")),
        NameMeaning("benjamin", "Son of the right hand", "دائیں ہاتھ کا بیٹا", "Hebrew", "Biblical", "Male", "4", "Blue", listOf("Beloved", "Steady", "Bright")),
        NameMeaning("elijah", "My God is the Lord", "میرا خدا خداوند ہے", "Hebrew", "Biblical", "Male", "8", "Gold", listOf("Prophetic", "Bold", "Devoted")),
        NameMeaning("salman", "Safe, peaceful", "محفوظ، پرسکون", "Arabic", "Islamic / Arabic", "Male", "5", "Green", listOf("Peaceful", "Kind", "Steady")),
        NameMeaning("imran", "Prosperity, flourishing", "ترقی، خوش حالی", "Arabic", "Islamic / Arabic", "Male", "7", "Green", listOf("Prosperous", "Patient", "Caring")),
        NameMeaning("khadija", "Premature child; trustworthy wife of the Prophet", "قابلِ اعتماد", "Arabic", "Islamic / Arabic", "Female", "6", "White", listOf("Trustworthy", "Wise", "Strong")),
        NameMeaning("hafsa", "Young lioness", "نوجوان شیرنی", "Arabic", "Islamic / Arabic", "Female", "4", "Gold", listOf("Brave", "Caring", "Devout")),
        NameMeaning("saad", "Good fortune, blessing", "خوش قسمتی، بھلائی", "Arabic", "Islamic / Arabic", "Male", "3", "Green", listOf("Fortunate", "Cheerful", "Kind")),
        NameMeaning("talha", "Tree of paradise", "جنت کا درخت", "Arabic", "Islamic / Arabic", "Male", "5", "Green", listOf("Generous", "Noble", "Steady")),
        NameMeaning("amir", "Prince, commander", "شہزادہ، سردار", "Arabic", "Islamic / Arabic", "Male", "2", "Purple", listOf("Leader", "Confident", "Just")),
        NameMeaning("farhan", "Happy, joyful", "خوش، مسرور", "Arabic", "Islamic / Arabic", "Male", "6", "Yellow", listOf("Joyful", "Friendly", "Warm")),
        NameMeaning("zara", "Blooming flower, princess", "کھلتا پھول، شہزادی", "Arabic / Hebrew", "Abrahamic", "Female", "3", "Pink", listOf("Radiant", "Elegant", "Bright")),
        NameMeaning("hana", "Happiness, bliss, flower", "خوشی، پھول", "Arabic / Japanese", "Global", "Female", "5", "Pink", listOf("Joyful", "Graceful", "Gentle")),
        NameMeaning("leonardo", "Brave lion", "بہادر شیر", "Germanic / Italian", "European", "Male", "7", "Gold", listOf("Creative", "Bold", "Visionary")),
        NameMeaning("peter", "Rock, stone", "چٹان، پتھر", "Greek", "Biblical", "Male", "9", "Blue", listOf("Solid", "Loyal", "Honest")),
        NameMeaning("maria", "Star of the sea; beloved", "سمندر کا ستارہ", "Hebrew / Latin", "Global", "Female", "2", "Blue", listOf("Devoted", "Graceful", "Steady"))
    ).associateBy { it.name }

    private val titles = mapOf(
        "mr" to "Male", "mrs" to "Female", "ms" to "Female", "miss" to "Female",
        "dr" to "Unisex", "sir" to "Male", "madam" to "Female", "prof" to "Unisex"
    )

    /** Normalises a free-form input to a lookup key (handles "Mr John", "John Smith"). */
    fun cleanName(raw: String): String {
        val parts = raw.trim().lowercase()
            .replace(Regex("[^a-z\\s'-]"), " ")
            .split(Regex("\\s+"))
            .filter { it.isNotBlank() && it !in titles }
        return parts.firstOrNull() ?: raw.trim().lowercase()
    }

    fun lookup(raw: String): NameMeaning? = names[cleanName(raw)]

    /** Fallback entry when nothing is known: still gives a useful, non-empty answer. */
    fun generated(raw: String, genderHint: String? = null, originHint: String? = null): NameMeaning {
        val clean = raw.trim().replaceFirstChar { it.uppercase() }
        val letter = clean.firstOrNull()?.uppercaseChar()?.toString() ?: "?"
        val seed = clean.lowercase().hashCode()
        val palette = listOf("Gold", "Blue", "Green", "Purple", "Red", "White")
        return NameMeaning(
            name = raw.trim(),
            meaning = "A distinctive personal name beginning with the letter \"$letter\". " +
                "Names of this shape are widely used across many cultures, and their meaning " +
                "usually comes from the language and family tradition they were chosen in. " +
                "Use the live sources below to see exactly how this name is described worldwide.",
            urduMeaning = "یہ ایک منفرد نام ہے جو حرف \"$letter\" سے شروع ہوتا ہے۔ اس نام کا مطلب اس کی زبان اور خاندانی روایت سے جڑا ہوتا ہے۔",
            origin = originHint ?: "Not confirmed — see live sources",
            culture = "Multi-cultural",
            gender = genderHint ?: "Unisex",
            luckyNumber = ((kotlin.math.abs(seed) % 9) + 1).toString(),
            luckyColor = palette[kotlin.math.abs(seed) % palette.size],
            traits = listOf("Distinctive", "Memorable", "Personal")
        )
    }
}
