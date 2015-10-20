<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="flights">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8"/>
    <title>Моя тестовая HTML-страница</title>
</head>
<body>
    <xsl:for-each select="flight">
        <table border="1" width="100%">
            <xsl:attribute name="id">
                <xsl:text>flight_certificates_</xsl:text>
                <xsl:value-of select="@flightCertificates"/>
            </xsl:attribute>
        </table>
    </xsl:for-each>
</body>
<style>td { vertical-align: top }</style>
</html>
</xsl:template>
</xsl:stylesheet>