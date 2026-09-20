package com.kodierer.focusflow

import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory
import org.w3c.dom.Element

/**
 * TDD-Guard für Issue #16 (FF-Security): Auto Backup muss deaktiviert sein.
 */
class BackupPolicyTest {

    private lateinit var manifestFile: File
    private lateinit var privacyFile: File

    @Before
    fun setup() {
        manifestFile = findUp("app/src/main/AndroidManifest.xml")
            ?: error("AndroidManifest.xml nicht gefunden (CWD=${File("").absolutePath})")
        privacyFile = findUp("docs/privacy.html")
            ?: error("docs/privacy.html nicht gefunden (CWD=${File("").absolutePath})")
    }

    @Test
    fun manifest_disables_allowBackup() {
        val factory = DocumentBuilderFactory.newInstance().apply { isNamespaceAware = true }
        val doc = factory.newDocumentBuilder().parse(manifestFile)
        val app = doc.getElementsByTagName("application").item(0) as Element
        val allowBackup = app.getAttributeNS("http://schemas.android.com/apk/res/android", "allowBackup")
        assertEquals("allowBackup muss \"false\" sein", "false", allowBackup)
    }

    @Test
    fun privacyHtml_discloses_noCloudBackup() {
        val text = privacyFile.readText()
        assertTrue(
            "privacy.html muss Auto Backup / Cloud Backup erwähnen",
            text.contains(Regex("Auto Backup|Cloud Backup|Cloud-Backup", RegexOption.IGNORE_CASE))
        )
        assertTrue(
            "privacy.html muss Löschung beim Deinstallieren/Reset erwähnen",
            text.contains(Regex("deinstall|In-App-Reset|in-app reset", RegexOption.IGNORE_CASE))
        )
    }

    private fun findUp(relativePath: String): File? {
        var dir: File = File("").canonicalFile
        repeat(8) {
            File(dir, relativePath).takeIf { it.exists() }?.let { return it }
            dir = dir.parentFile ?: return null
        }
        return null
    }
}