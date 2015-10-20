<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" version="4.0" encoding="UTF-8" indent="yes"/>
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
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Время отправления</th>
                    <th>Время прибытия</th>
                    <th>Длит. полета</th>
                    <th>Пилот</th>
                    <th>Самолет</th>
                    <th>Маршрут</th>
                    <th>Билет</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><xsl:value-of select="id"/></td>
                    <td>
                        <xsl:call-template name="beatify_date">
                            <xsl:with-param name="date" select="timeOfDeparture"/>
                        </xsl:call-template>
                    </td>
                    <td>
                        <xsl:call-template name="beatify_date">
                            <xsl:with-param name="date" select="timeOfArrival"/>
                        </xsl:call-template>
                    </td>
                    <td>Difference With Dates</td>
                    <td>
                        <xsl:for-each select="pilot">
                            <xsl:call-template name="describe_pilot">
                                <xsl:with-param name="pilot" select="pilot"/>
                            </xsl:call-template>
                        </xsl:for-each>
                    </td>
                    <td>
                        <xsl:for-each select="plane">
                            <xsl:call-template name="describe_plane">
                                <xsl:with-param name="plane" select="plane"/>
                            </xsl:call-template>
                        </xsl:for-each>
                    </td>
                    <td>
                        <xsl:for-each select="route">
                            <xsl:call-template name="describe_route">
                                <xsl:with-param name="route" select="route"/>
                            </xsl:call-template>
                        </xsl:for-each>
                    </td>
                    <td>
                        <xsl:for-each select="ticket">
                            <xsl:sort select="id"/>
                            <xsl:call-template name="describe_ticket">
                                <xsl:with-param name="ticket" select="ticket"/>
                            </xsl:call-template>
                        </xsl:for-each>
                    </td>
                </tr>
            </tbody>
            <tfoot>
                <tr>
                    <td>Итого продано: <xsl:value-of select="count(ticket)"/></td>
                </tr>
                <tr>
                    <td>Итоговая сумма: <xsl:value-of select="sum(ticket/price)"/></td>
                </tr>
            </tfoot>
        </table>
        <br/>
        <br/>
    </xsl:for-each>
</body>
<style>td { vertical-align: top }</style>
</html>
</xsl:template>
    
<xsl:template name="describe_ticket">
    <xsl:param name="ticket" />
    <table border="1" width="100%">
        <caption>Билет № <xsl:number/></caption>
        <tr>
            <td>ID</td>
            <td><xsl:value-of select="id"/></td>
        </tr>
        <tr>
            <td>Ряд</td>
            <td><xsl:value-of select="row"/></td>
        </tr>
        <tr>
            <td>Место</td>
            <td><xsl:value-of select="place"/></td>
        </tr>
        <tr>
            <td>Статус</td>
            <td><xsl:value-of select="status"/></td>
        </tr>
        <tr>
            <td>Цена</td>
            <td><xsl:value-of select="price"/></td>
        </tr>
        <tr>
            <td>Расположение</td>
            <td><xsl:value-of select="@position"/></td>
        </tr>
    </table>
</xsl:template>    

<xsl:template name="describe_route">
    <xsl:param name="route" />
    <table border="1" width="100%">
        <tr>
            <td>ID</td>
            <td><xsl:value-of select="id"/></td>
        </tr>
        <tr>
            <td>Название</td>
            <td><xsl:value-of select="title"/></td>
        </tr>
        <tr>
            <td>Шифр</td>
            <td><xsl:value-of select="cipher"/></td>
        </tr>
        <tr>
            <td>Дистанция</td>
            <td><xsl:value-of select="distance"/></td>
        </tr>
        <xsl:for-each select="point">
            <tr>
                <td>Точка <xsl:number/></td>
                <td/>
            </tr>
            <tr>
                <td>ID<xsl:number/></td>
                <td><xsl:value-of select="id"/></td>
            </tr>
            <tr>
                <td>Страна<xsl:number/></td>
                <td><xsl:value-of select="country"/></td>
            </tr>
            <tr>
                <td>Город<xsl:number/></td>
                <td><xsl:value-of select="city"/></td>
            </tr>
            <tr>
                <td>Картинка<xsl:number/></td>
                <td><xsl:value-of select="@image"/></td>
            </tr>
        </xsl:for-each>
    </table>
</xsl:template>

<xsl:template name="describe_plane">
    <xsl:param name="plane" />
    <table border="1" width="100%">
        <tr>
            <td>ID</td>
            <td><xsl:value-of select="id"/></td>
        </tr>
        <tr>
            <td>Модель</td>
            <td><xsl:value-of select="model"/></td>
        </tr>
        <tr>
            <td>Год</td>
            <td><xsl:value-of select="year"/></td>
        </tr>
        <tr>
            <td>Количество рядов</td>
            <td><xsl:value-of select="countOfRows"/></td>
        </tr>
        <tr>
            <td>Мест в ряду</td>
            <td><xsl:value-of select="placesInRow"/></td>
        </tr>
    </table>
</xsl:template>

<xsl:template name="describe_pilot">
    <xsl:param name="pilot" />
    <table border="1" width="100%">
        <caption>Пилот <xsl:number/></caption>
        <tr>
            <td>ID</td>
            <td><xsl:value-of select="id"/></td>
        </tr>
        <tr>
            <td>Фамилия</td>
            <td><xsl:value-of select="surname"/></td>
        </tr>
        <tr>
            <td>Имя</td>
            <td><xsl:value-of select="name"/></td>
        </tr>
        <tr>
            <td>Отчество</td>
            <td><xsl:value-of select="patronymic"/></td>
        </tr>
        <tr>
            <td>Категория</td>
            <td><xsl:value-of select="category"/></td>
        </tr>
        <tr>
            <td>Адрес</td>
            <td><xsl:value-of select="@address"/></td>
        </tr>
        <tr>
            <td>Номер сертификата</td>
            <td><xsl:value-of select="@certificateNumber"/></td>
        </tr>
        <tr>
            <td>EMail</td>
            <td><xsl:value-of select="@eMail"/></td>
        </tr>
        <tr>
            <td>Номер телефона</td>
            <td><xsl:value-of select="@phoneNumber"/></td>
        </tr>
    </table>
</xsl:template>

<xsl:template name = "beatify_date" >
    <xsl:param name = "date" />
    <xsl:call-template name="string_replace">
        <xsl:with-param name="input" select="$date"/>
        <xsl:with-param name="newValue" select="' '"/>
        <xsl:with-param name="oldValue" select="'T'"/>
    </xsl:call-template>
</xsl:template>

<xsl:template name="string_replace">
    <xsl:param name="input" />
    <xsl:param name="oldValue" />
    <xsl:param name="newValue" />
    <xsl:choose>
        <xsl:when test="contains($input, $oldValue)">
            <xsl:value-of select="substring-before($input,$oldValue)" /> <!--Возвращает подстроку первой строки аргумента, стоящей до первого вхождения второй строки аргумента в первой строке.  -->
            <xsl:value-of select="$newValue" />
            <xsl:call-template name="string_replace">
                <xsl:with-param name="input" select="substring-after($input,$oldValue)" /> <!--Возвращает подстроку первой строки аргумента, которая следует за первым вхождением строки второго аргумента в строке первого аргумента-->
                <xsl:with-param name="oldValue" select="$oldValue" />
                <xsl:with-param name="newValue" select="$newValue" />
            </xsl:call-template>
        </xsl:when>
        <xsl:otherwise>
            <xsl:value-of select="$input" />
        </xsl:otherwise>
    </xsl:choose>
</xsl:template>

</xsl:stylesheet>