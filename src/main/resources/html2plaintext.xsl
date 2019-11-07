<?xml version="1.0" encoding="UTF-8"?>
<!-- 
	Dit stylesheet vertaalt html uit de editor in EduArte naar platte tekst.
	De elementen en attributen uit CobrAWhitelist#cobraDefault() worden ondersteund. 
-->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="text" indent="no" encoding="UTF-8"/>
	
	<xsl:strip-space elements="*"/>
	
	<!-- Match de root en alle elementen -->
	<xsl:template match="/|*">
		<xsl:apply-templates select="node()"/>
	</xsl:template>
	
	<!-- Plaats twee regeleindes voor een <p>. -->
	<xsl:template match="p">
		<xsl:text>&#xA;&#xA;</xsl:text>
		<xsl:apply-templates select="node()"/>
	</xsl:template>
	
	<!-- Plaats een regeleinde voor de gematchte elementen. -->
	<xsl:template match="div|table|tr[preceding-sibling::*]|dl|dt">
		<xsl:text>&#xA;</xsl:text>
		<xsl:apply-templates select="node()"/>
	</xsl:template>
	
	<!-- Toon opsommingen met een witregel boven en onder. -->
	<xsl:template match="ul|ol">
		<xsl:if test="preceding-sibling::*">
			<xsl:text>&#xA;</xsl:text>
		</xsl:if>
		<xsl:text>&#xA;</xsl:text>
		<xsl:apply-templates select="node()"/>
		<xsl:if test="following-sibling::text()[1][normalize-space(.)]">
			<xsl:text>&#xA;</xsl:text>
		</xsl:if>
		<xsl:text>&#xA;</xsl:text>
	</xsl:template>
	
	<!-- Plaats een nummer voor een list-item in een geordende lijst. -->
	<xsl:template match="li">
		<xsl:if test="preceding-sibling::li">
			<xsl:text>&#xA;</xsl:text>
		</xsl:if>
		<xsl:choose>
			<xsl:when test="parent::ol">
				<xsl:value-of select="count(preceding-sibling::li)+1"/>
				<xsl:text>. </xsl:text>
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>&#x2022; </xsl:text>
			</xsl:otherwise>
		</xsl:choose>
		<xsl:apply-templates select="node()"/>
	</xsl:template>
	
	<!-- Toon een blockquote met een witregel voor en na, eerste regel drie spaties ingesprongen, het eventuele cite-attribuut wordt op de volgende regel getoond. -->
	<xsl:template match="blockquote">
		<xsl:text>&#xA;&#xA;</xsl:text>
		<xsl:apply-templates select="node()"/>
		<xsl:if test="@cite">
			<xsl:text>&#xA;</xsl:text>
			<xsl:value-of select="@cite"/>
		</xsl:if>
		<xsl:text>&#xA;&#xA;</xsl:text>
	</xsl:template>
	
	<!-- Zet een quote tussen aanhalingstekens, zet de cite er achter tussen haakjes. -->
	<xsl:template match="q">
		<xsl:text>&quot;</xsl:text>
		<xsl:apply-templates select="node()"/>
		<xsl:text>&quot;</xsl:text>
		<xsl:if test="@cite">
			<xsl:text> (</xsl:text>
			<xsl:value-of select="@cite"/>
			<xsl:text>)</xsl:text>
		</xsl:if>
	</xsl:template>
	
	<!-- Scheid tabelcellen met een |, dit gaat alleen maar goed bij eenvoudige tabellen. -->
	<xsl:template match="td[preceding-sibling::*]|th[preceding-sibling::*]">
		<xsl:text> | </xsl:text>
		<xsl:apply-templates select="node()"/>
	</xsl:template>
	
	<!-- Spring een dd in met drie spaties en plaats er een spatie voor. -->
	<xsl:template match="dd">
		<xsl:text>&#xA;   </xsl:text>
		<xsl:apply-templates select="node()"/>
	</xsl:template>
	
	<!-- Plaats een witregel voor een h[1-6] en een regeleinde er na. -->
	<xsl:template match="h1|h2|h3|h4|h5|h6">
		<xsl:text>&#xA;&#xA;</xsl:text>
		<xsl:apply-templates select="node()"/>
		<xsl:text>&#xA;</xsl:text>
	</xsl:template>
	
	<!-- Toont de alt van een img, gevolgd door de src tussen haakjes. -->
	<xsl:template match="img">
		<xsl:if test="@alt">
			<xsl:value-of select="@alt"/>
			<xsl:text> </xsl:text>
		</xsl:if>
		<xsl:if test="@href">
			<xsl:text>(</xsl:text>
			<xsl:value-of select="@href"/>
			<xsl:text>)</xsl:text>
		</xsl:if>
	</xsl:template>
	
	<!-- Plaats een regeleinde na een <br/> -->
	<xsl:template match="br">
		<xsl:apply-templates select="node()"/>
		<xsl:text>&#xA;</xsl:text>
	</xsl:template>
	
	<!-- Toont de href van een link tussen haakjes achter de tekst van de link -->
	<xsl:template match="a[@href]">
		<xsl:apply-templates select="node()"/>
		<xsl:text> (</xsl:text>
		<xsl:value-of select="@href"/>
		<xsl:text>) </xsl:text>
	</xsl:template>
	
	<!-- Verwijder witruimte aan begin en eind van de tekst. -->
	<xsl:template match="text()">
		<xsl:value-of select="normalize-space(.)"/>
	</xsl:template>
	
	 <xsl:template match="//*[@value='p@gmail.com']">
		<xsl:apply-templates select="node()"/>
		<xsl:text>()</xsl:text>
	</xsl:template>
</xsl:stylesheet>
