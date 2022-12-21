import java.awt.Dimension
import java.net.URL
import javax.swing.*

class Window : JFrame() {

    val minSz = Dimension(400, 450)

    val regHref = Regex(
        "(?:\\b)(?:(?:https:\\/\\/)|(?:www.))([\\d\\w-]+)\\.(\\w{2,6})([\\/\\d\\w-_\\.=\\?%&\\\"]*)\\/?(?:\\b)",
        RegexOption.MULTILINE
    )

    val regEmail = Regex(
        "(?<!\\w)([\\w\\d_\\.\\-]+)@([\\w\\d_\\.\\-]+)\\.([a-z\\.]{2,6})(\\s)",
        RegexOption.MULTILINE
    )

    init {

        val textPane = JTextPane()
        textPane.apply{
            contentType = "text/html"
            text = URL("https://edu.kpfu.ru").readText()
        }
        val b = JButton("click here")
        b.addActionListener() {
            val txt1 = URL("https://edu.kpfu.ru").readText()
            val txt2 = "<".toRegex().replace(txt1,"&lt;")
            val txt3 = ">".toRegex().replace(txt2,"&gt;")
            val txt = regEmail.replace(txt3) {
                "<a href = ${it.value}>${it.value}</a>"
            }

            textPane.text = regHref.replace(txt){
                "<a href = ${it.value}>${it.value}</a>"
            }
        }

        val scrPane = JScrollPane(textPane)

        minimumSize = minSz

        layout = GroupLayout(contentPane).apply {
            setHorizontalGroup(
                createSequentialGroup()
                    .addGap(8)
                    .addGroup(
                        createParallelGroup(GroupLayout.Alignment.CENTER)
                            .addComponent(scrPane, 350, 1080, GROW)
                            .addComponent(b, SHRINK, SHRINK, SHRINK)
                    )
                    .addGap(8)
            )

            setVerticalGroup(
                createSequentialGroup()
                    .addGap(8)
                    .addComponent(scrPane, 350, 1920, GROW)
                    .addGap(8)
                    .addComponent(b, SHRINK, SHRINK, SHRINK)
                    .addGap(8)
            )
        }
    }

    companion object {
        const val GROW = GroupLayout.DEFAULT_SIZE
        const val SHRINK = GroupLayout.PREFERRED_SIZE
    }
}