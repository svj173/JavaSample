<?xml version="1.0" encoding="koi8-r"?>
<xsl:stylesheet version="1.0" xmlns="http://www.w3.org/TR/REC-html40"
  xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:variable name="space_before_manager">
        <xsl:choose>
                <xsl:when test="/form/stamp/stampfacturamanager/graphix">-40.00pt</xsl:when>
                <xsl:otherwise>51.00pt</xsl:otherwise>
        </xsl:choose>
  </xsl:variable>
  <xsl:variable name="space_before_accountant">
        <xsl:choose>
                <xsl:when test="/form/stamp/stampfacturaaccountant/graphix">-40.00pt</xsl:when>
                <xsl:otherwise>51.00pt</xsl:otherwise>
        </xsl:choose>
  </xsl:variable>
  <xsl:output indent="no" method="xml"/>
  <xsl:param name="page-count">0</xsl:param>

  <xsl:template match="/">
<!--    <xsl:for-each select="/former/call[position() mod $count-on-page = 1]"-->
    <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">

      <fo:layout-master-set>
        <fo:simple-page-master margin-bottom="0.5in" margin-left="18pt"
            margin-right="18pt" margin-top="0.2in" master-name="simple"
            page-height="8.5in" page-width="11.0in">
          <fo:region-body margin-bottom="0.2in" margin-top="0.2in"/>
          <fo:region-before extent="0in"/>
          <fo:region-after extent="0in"/>
        </fo:simple-page-master>

      </fo:layout-master-set>

      <fo:page-sequence master-reference="simple">

        <fo:static-content flow-name="xsl-region-after">
          <!--
          <fo:block>
            Page <fo:page-number/>  of <fo:page-number-citation ref-id="last-page"/> 
            <xsl:value-of select="$page-count"/>
            <xsl:if test="$page-count != 1">   <xsl:text>-AAA</xsl:text>        </xsl:if>
          </fo:block>
          -->
          <fo:block font-family="Arial" font-size="7.00pt"
              line-height="7.00pt" space-after="1.00pt" text-align="end"
              vertical-align="text-top">
            <xsl:if test="$page-count != 1">
               <!--fo:page-number/>  из <fo:page-number-citation ref-id="last-page"/-->
               <fo:page-number/>  из <xsl:value-of select="$page-count"/>
            </xsl:if>
          </fo:block>
        </fo:static-content>

        <fo:flow flow-name="xsl-region-body">
          <fo:block font-family="Arial" font-size="7.00pt"
            line-height="7.00pt" space-after="1.00pt" text-align="end"
            vertical-align="text-top">Приложение N1</fo:block>
          <fo:block font-family="Arial" font-size="7.00pt"
            line-height="7.00pt" space-after="1.00pt" text-align="end"
            vertical-align="text-top">к Правилам ведения журналов учета полученных и выставленных счетов-фактур,</fo:block>
          <fo:block font-family="Arial" font-size="7.00pt"
            line-height="7.00pt" space-after="1.00pt" text-align="end"
            vertical-align="text-top">книг покупок и книг продаж при расчетах по налогу на добавленную стоимость,</fo:block>
          <fo:block font-family="Arial" font-size="7.00pt"
            line-height="7.00pt" space-after="1.00pt" text-align="end"
            vertical-align="text-top">утвержденным постановлением правительства Российской Федерации от 2 декабря 2000 г. N 914</fo:block>
          <fo:block font-family="Arial" font-size="7.00pt"
            line-height="7.00pt" space-after="1.00pt" text-align="end"
            vertical-align="text-top">(в редакции постановления правительства Российской Федерации от 11 мая 2006 г. N 283)</fo:block>
          <fo:block font-family="Arial" font-size="14.00pt"
            font-weight="bold" line-height="14.00pt" text-align="start"
            vertical-align="text-top">Счет-фактура <xsl:value-of
            select="/form/number"/> от <xsl:value-of select="/form/create-date"/>
          </fo:block>
          <fo:block font-family="Arial" font-size="8.00pt"
            line-height="8.00pt" space-after="0.70pt"
            space-before="0.70pt" text-align="start"
            vertical-align="text-top">Продавец: <xsl:value-of select="/form/base-name"/></fo:block>
          <fo:block font-family="Arial" font-size="8.00pt"
            line-height="8.00pt" space-after="0.70pt"
            space-before="0.70pt" text-align="start"
            vertical-align="text-top">Адрес: <xsl:value-of select="/form/base-law-address"/></fo:block>
          <fo:block font-family="Arial" font-size="8.00pt"
            line-height="8.00pt" space-after="0.70pt"
            space-before="0.70pt" text-align="start"
            vertical-align="text-top">ИНН/КПП продавца <xsl:value-of select="/form/base-inn"/><xsl:value-of select="/form/base-kpp"/></fo:block>
          <fo:block font-family="Arial" font-size="8.00pt"
            line-height="8.00pt" space-after="0.70pt"
            space-before="0.70pt" text-align="start"
            vertical-align="text-top">Грузоотправитель и его адрес: <xsl:value-of select="/form/base-name"/>, <xsl:value-of select="/form/base-address"/></fo:block>
          <fo:block font-family="Arial" font-size="8.00pt"
            line-height="8.00pt" space-after="0.70pt"
            space-before="0.70pt" text-align="start"
            vertical-align="text-top">Грузополучатель и его адрес: 

            <xsl:value-of select="/form/consignee-name"/>
						<xsl:choose>
							<xsl:when test="/form/consignee-comma">, </xsl:when>
							<xsl:otherwise></xsl:otherwise>
						</xsl:choose>
						<xsl:choose>
							<xsl:when test="/form/consignee-name = '-'"><xsl:value-of select="/form/consignee-name"/></xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="/form/consignee-actual-address-full/core.storable.Address/svj.zip"/>,
								<xsl:value-of select="/form/consignee-actual-address-full/core.storable.State/name"/>,  
								г. <xsl:value-of select="/form/consignee-actual-address-full/core.storable.City/name"/>, 
								<xsl:value-of select="/form/consignee-actual-address-full/core.storable.Street/name"/>
								<xsl:choose>
									<xsl:when test="/form/consignee-actual-address-full/core.storable.Address/num = ''"></xsl:when>
									<xsl:otherwise>, д. <xsl:value-of select="/form/consignee-actual-address-full/core.storable.Address/num"/></xsl:otherwise>
								</xsl:choose>
								<xsl:choose>
									<xsl:when test="/form/consignee-actual-address-full/core.storable.Address/building = ''"></xsl:when>
									<xsl:otherwise>, стр. <xsl:value-of select="/form/consignee-actual-address-full/core.storable.Address/building"/></xsl:otherwise>   
								</xsl:choose>
								<xsl:choose>
									<xsl:when test="/form/consignee-actual-address-full/core.storable.Address/block = ''"></xsl:when>
									<xsl:otherwise>, кор. <xsl:value-of select="/form/consignee-actual-address-full/core.storable.Address/block"/></xsl:otherwise>   
								</xsl:choose>
								<xsl:choose>
									<xsl:when test="/form/consignee-actual-address-full/core.storable.Address/flat = ''"></xsl:when>
									<xsl:otherwise>, оф. <xsl:value-of select="/form/consignee-actual-address-full/core.storable.Address/flat"/></xsl:otherwise>   
								</xsl:choose>
							</xsl:otherwise>
						</xsl:choose>
					</fo:block>



          <fo:block font-family="Arial" font-size="8.00pt"
            line-height="8.00pt" space-after="0.70pt"
            space-before="0.70pt" text-align="start"
            vertical-align="text-top">К платежно-расчетному документу 
                <xsl:for-each select="/form/paymentImprests/paymentImprest">
		<xsl:if test="orderNum != ''">
		  &#8470; <xsl:value-of select="orderNum"/>
		</xsl:if>
		<xsl:if test="paymentDate/short != ''">
		  от <xsl:value-of select="paymentDate/short"/>
		</xsl:if>
                </xsl:for-each>
	  </fo:block>
          <fo:block font-family="Arial" font-size="8.00pt"
            line-height="8.00pt" space-after="0.70pt"
            space-before="0.70pt" text-align="start"
            vertical-align="text-top">Покупатель: <xsl:value-of select="/form/company-name"/></fo:block>
          <fo:block font-family="Arial" font-size="8.00pt"
            line-height="8.00pt" space-after="0.70pt"
            space-before="0.70pt" text-align="start"
            vertical-align="text-top">Адрес: 

		<xsl:value-of select="/form/company-law-address-full/core.storable.Country/name"/>,&#160;
		<xsl:value-of select="/form/company-law-address-full/core.storable.Address/svj.zip"/>,&#160;
		<xsl:choose>
		  <xsl:when test="/form/company-law-address-full/core.storable.City/name = 'Москва'
			      or  /form/company-law-address-full/core.storable.City/name = 'Пермь'
			      or  /form/company-law-address-full/core.storable.City/name = 'Кемерово'
			      or  /form/company-law-address-full/core.storable.City/name = 'Омск'
			      or  /form/company-law-address-full/core.storable.City/name = 'Томск'
			      or  /form/company-law-address-full/core.storable.City/name = 'Новосибирск'">
		  </xsl:when>
		  <xsl:otherwise>
		    <xsl:value-of select="/form/company-law-address-full/core.storable.State/name"/>,&#160;
		  </xsl:otherwise>
		</xsl:choose>
		г. <xsl:value-of select="/form/company-law-address-full/core.storable.City/name"/>,&#160;
		<xsl:choose>
		  <xsl:when test="/form/company-law-address-full/core.storable.Address/postBox != ''">
		    а/я <xsl:value-of select="/form/company-law-address-full/core.storable.Address/postBox"/>
		  </xsl:when>
	          <xsl:otherwise>
		    <xsl:value-of select="/form/company-law-address-full/core.storable.Street/name"/>
		      ,&#160; <xsl:value-of select="/form/company-law-address-full/core.storable.Address/num"/>
		    <xsl:if test="/form/company-law-address-full/core.storable.Address/building !=''">
		      ,&#160;стр. <xsl:value-of select="/form/company-law-address-full/core.storable.Address/building"/>
		    </xsl:if>

		    <xsl:if test="/form/company-law-address-full/core.storable.Address/block !=''">
		      ,&#160;корп. <xsl:value-of select="/form/company-law-address-full/core.storable.Address/block"/>
		    </xsl:if>

		    <xsl:if test="/form/company-law-address-full/core.storable.Address/flat !=''">
 		      ,&#160;оф. <xsl:value-of select="/form/company-law-address-full/core.storable.Address/flat"/>
		    </xsl:if>
		  </xsl:otherwise>
		</xsl:choose>

          </fo:block>
          <fo:block font-family="Arial" font-size="8.00pt"
            line-height="8.00pt" space-after="0.70pt"
            space-before="0.70pt" text-align="start"
            vertical-align="text-top">ИНН/КПП покупателя <xsl:value-of select="/form/company-inn"/><xsl:value-of select="/form/consignee-kpp"/></fo:block>
          <fo:block font-family="Arial" font-size="8.00pt"
            line-height="8.00pt" space-after="0.70pt"
            space-before="0.70pt" text-align="start"
            vertical-align="text-top"><xsl:value-of select="/form/documentname"/>&#160;<xsl:value-of select="/form/documentnumber"/>&#160;от&#160;<xsl:value-of select="/form/dogovor-date"/>&#160;л.с.&#160;<xsl:value-of select="/form/account"/></fo:block>
          <fo:block font-family="Arial" font-size="8.00pt"
            font-weight="bold" line-height="8.00pt"
            space-after="0.70pt" space-before="0.70pt"
            start-indent="4.00pt" text-align="end"
            vertical-align="text-top">Валюта: руб.</fo:block>
          <fo:table table-layout="fixed">
            <fo:table-column column-width="5.5cm"/>
            <fo:table-column column-width="1.3cm"/>
            <fo:table-column column-width="1.8cm"/>
            <fo:table-column column-width="2cm"/>
            <fo:table-column column-width="2.4cm"/>
            <fo:table-column column-width="1.3cm"/>
            <fo:table-column column-width="1.7cm"/>
            <fo:table-column column-width="2.1cm"/>
            <fo:table-column column-width="2.4cm"/>
            <fo:table-column column-width="2.5cm"/>
            <fo:table-column column-width="3.7cm"/>
            <fo:table-header>
               <fo:table-row>
                  <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" space-before="4.70pt"
                      text-align="center" vertical-align="text-bottom"
                      wrap-option="no-wrap">Наименование товара (описание</fo:block>
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" text-align="center"
                      vertical-align="text-bottom"
                      wrap-option="no-wrap">выполненных работ, оказанных</fo:block>
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" text-align="center"
                      vertical-align="text-bottom" wrap-option="no-wrap">услуг, имущественного права)</fo:block>
                  </fo:table-cell>
                  <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" space-before="4.70pt"
                      text-align="center" vertical-align="text-bottom" wrap-option="no-wrap">Единица</fo:block>
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" text-align="center"
                      vertical-align="text-bottom" wrap-option="no-wrap">изме-</fo:block>
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" text-align="center"
                      vertical-align="text-bottom" wrap-option="no-wrap">рения</fo:block>
                  </fo:table-cell>
                  <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" space-before="7.70pt"
                      text-align="center" vertical-align="text-bottom" wrap-option="no-wrap">Коли-</fo:block>
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" text-align="center"
                      vertical-align="text-bottom" wrap-option="no-wrap">чество</fo:block>
                  </fo:table-cell>
                  <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" space-before="4.70pt"
                      text-align="center" vertical-align="text-bottom"
                      wrap-option="no-wrap">Цена (тариф)</fo:block>
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" text-align="center"
                      vertical-align="text-bottom"
                      wrap-option="no-wrap">за единицу</fo:block>
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" text-align="center"
                      vertical-align="text-bottom" wrap-option="no-wrap">измерения</fo:block>
                  </fo:table-cell>
                  <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" text-align="center"
                      vertical-align="text-bottom" wrap-option="no-wrap">Стоимость</fo:block>
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" text-align="center"
                      vertical-align="text-bottom"
                      wrap-option="no-wrap">товаров (работ,</fo:block>
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" text-align="center"
                      vertical-align="text-bottom"
                      wrap-option="no-wrap">услуг,</fo:block>
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" text-align="center"
                      vertical-align="text-bottom"
                      wrap-option="no-wrap">имущественных</fo:block>
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" text-align="center"
                      vertical-align="text-bottom"
                      wrap-option="no-wrap">прав), всего</fo:block>
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" space-after="0.70pt"
                      text-align="center" vertical-align="text-bottom" wrap-option="no-wrap">налога</fo:block>
                  </fo:table-cell>
                  <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" space-before="4.70pt"
                      text-align="center" vertical-align="text-bottom"
                      wrap-option="no-wrap">В том</fo:block>
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" text-align="center"
                      vertical-align="text-bottom" wrap-option="no-wrap">числе</fo:block>
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" text-align="center"
                      vertical-align="text-bottom" wrap-option="no-wrap">акциз</fo:block>
                  </fo:table-cell>
                  <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" space-before="7.70pt"
                      text-align="center" vertical-align="text-bottom" wrap-option="no-wrap">Налоговая</fo:block>
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" text-align="center"
                      vertical-align="text-bottom" wrap-option="no-wrap">ставка</fo:block>
                  </fo:table-cell>
                  <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" space-before="10.70pt"
                      text-align="center" vertical-align="text-bottom"
                      wrap-option="no-wrap">Сумма налога</fo:block>
                  </fo:table-cell>
                  <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" text-align="center"
                      vertical-align="text-bottom" wrap-option="no-wrap">Стоимость</fo:block>
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" text-align="center"
                      vertical-align="text-bottom"
                      wrap-option="no-wrap">товаров (работ,</fo:block>
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" text-align="center"
                      vertical-align="text-bottom"
                      wrap-option="no-wrap">услуг, </fo:block>
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" text-align="center"
                      vertical-align="text-bottom"
                      wrap-option="no-wrap">имущественных</fo:block>
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" text-align="center"
                      vertical-align="text-bottom"
                      wrap-option="no-wrap">прав), всего с</fo:block>
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" space-after="0.70pt"
                      text-align="center" vertical-align="text-bottom"
                      wrap-option="no-wrap">учетом налога</fo:block>
                  </fo:table-cell>
                  <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" space-before="4.70pt"
                      text-align="center" vertical-align="text-bottom" wrap-option="no-wrap">Страна</fo:block>
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" text-align="center"
                      vertical-align="text-bottom" wrap-option="no-wrap">происхож-</fo:block>
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" text-align="center"
                      vertical-align="text-bottom" wrap-option="no-wrap">дения</fo:block>
                  </fo:table-cell>
                  <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" text-align="center"
                      vertical-align="text-bottom" wrap-option="no-wrap">Номер</fo:block>
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" text-align="center"
                      vertical-align="text-bottom" wrap-option="no-wrap">таможенной</fo:block>
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" space-after="0.70pt"
                      text-align="center" vertical-align="text-bottom" wrap-option="no-wrap">декларации</fo:block>
                  </fo:table-cell>
                </fo:table-row>
               <fo:table-row>
                  <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" space-after="1.00pt"
                      space-before="1.00pt" text-align="center" wrap-option="no-wrap">1</fo:block>
                  </fo:table-cell>
                  <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" space-after="1.00pt"
                      space-before="1.00pt" text-align="center" wrap-option="no-wrap">2</fo:block>
                  </fo:table-cell>
                  <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" space-after="1.00pt"
                      space-before="1.00pt" text-align="center" wrap-option="no-wrap">3</fo:block>
                  </fo:table-cell>
                  <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" space-after="1.00pt"
                      space-before="1.00pt" text-align="center" wrap-option="no-wrap">4</fo:block>
                  </fo:table-cell>
                  <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" space-after="1.00pt"
                      space-before="1.00pt" text-align="center" wrap-option="no-wrap">5</fo:block>
                  </fo:table-cell>
                  <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" space-after="1.00pt"
                      space-before="1.00pt" text-align="center" wrap-option="no-wrap">6</fo:block>
                  </fo:table-cell>
                  <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" space-after="1.00pt"
                      space-before="1.00pt" text-align="center" wrap-option="no-wrap">7</fo:block>
                  </fo:table-cell>
                  <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" space-after="1.00pt"
                      space-before="1.00pt" text-align="center" wrap-option="no-wrap">8</fo:block>
                  </fo:table-cell>
                  <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" space-after="1.00pt"
                      space-before="1.00pt" text-align="center" wrap-option="no-wrap">9</fo:block>
                  </fo:table-cell>
                  <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" space-after="1.00pt"
                      space-before="1.00pt" text-align="center" wrap-option="no-wrap">10</fo:block>
                  </fo:table-cell>
                  <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                    <fo:block font-family="Arial" font-size="8.00pt"
                      line-height="8.00pt" space-after="1.00pt"
                      space-before="1.00pt" text-align="center" wrap-option="no-wrap">11</fo:block>
                  </fo:table-cell>
                </fo:table-row>
            </fo:table-header>
            <fo:table-body>
              <xsl:for-each select="/form/invoice-item">
              <fo:table-row keep-together="always">
                <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                  <fo:block font-family="Arial" font-size="8.00pt" start-indent="2pt" text-indent="5pt"
                    line-height="8.00pt" text-align="start" space-before="1.00pt"><xsl:value-of select="name"/></fo:block>
                </fo:table-cell>
                <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                  <fo:block font-family="Arial" font-size="8.00pt"
                    line-height="8.00pt" space-after="1.00pt"
                    space-before="1.00pt" text-align="center" wrap-option="no-wrap"><xsl:value-of select="quantity"/></fo:block>
                </fo:table-cell>
                <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                  <fo:block end-indent="2pt" font-family="Arial"
                    font-size="8.00pt" line-height="8.00pt"
                    space-after="1.00pt" space-before="1.00pt"
                    text-align="end" wrap-option="no-wrap"><xsl:value-of select="amount"/></fo:block>
                </fo:table-cell>

<!--                <xsl:choose>-->
<!--                  <xsl:when test="/form/use-rur">-->
                    <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                      <fo:block end-indent="2pt" font-family="Arial"
                        font-size="8.00pt" line-height="8.00pt"
                        space-after="1.00pt" space-before="1.00pt"
                        text-align="end" wrap-option="no-wrap"><xsl:value-of select="price"/></fo:block>
                    </fo:table-cell>
                    <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                      <fo:block end-indent="2pt" font-family="Arial"
                        font-size="8.00pt" line-height="8.00pt"
                        space-after="1.00pt" space-before="1.00pt"
                        text-align="end" wrap-option="no-wrap"><xsl:value-of select="total"/></fo:block>
                    </fo:table-cell>
<!--                    </xsl:when>-->
<!--                    <xsl:otherwise>-->
<!--                      <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">-->
<!--                        <fo:block end-indent="2pt" font-family="Arial"-->
<!--                          font-size="8.00pt" line-height="8.00pt"-->
<!--                          space-after="1.00pt" space-before="1.00pt"-->
<!--                          text-align="end" wrap-option="no-wrap"><xsl:value-of select="amount-usd"/></fo:block>-->
<!--                      </fo:table-cell>-->
<!--                      <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">-->
<!--                        <fo:block end-indent="2pt" font-family="Arial"-->
<!--                          font-size="8.00pt" line-height="8.00pt"-->
<!--                          space-after="1.00pt" space-before="1.00pt"-->
<!--                          text-align="end" wrap-option="no-wrap"><xsl:value-of select="amount-usd"/></fo:block>-->
<!--                      </fo:table-cell>-->
<!--                    </xsl:otherwise>-->
<!--                  </xsl:choose>-->

                <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                  <fo:block end-indent="2pt" font-family="Arial"
                    font-size="8.00pt" line-height="8.00pt"
                    space-after="1.00pt" space-before="1.00pt"
                    text-align="end" wrap-option="no-wrap">----</fo:block>
                </fo:table-cell>

                <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                  <fo:block font-family="Arial" font-size="8.00pt"
                    line-height="8.00pt" space-after="1.00pt"
                    space-before="1.00pt" text-align="center" wrap-option="no-wrap"><xsl:value-of select="vat-rate"/></fo:block>
                </fo:table-cell>


<!--                <xsl:choose>-->
<!--                  <xsl:when test="/form/use-rur">-->
                    <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                      <fo:block end-indent="2pt" font-family="Arial"
                        font-size="8.00pt" line-height="8.00pt"
                        space-after="1.00pt" space-before="1.00pt"
                        text-align="end" wrap-option="no-wrap">
                        <xsl:value-of select="nds"/>
                      </fo:block>
                    </fo:table-cell>
                    <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                      <fo:block end-indent="2pt" font-family="Arial"
                        font-size="8.00pt" line-height="8.00pt"
                        space-after="1.00pt" space-before="1.00pt"
                        text-align="end" wrap-option="no-wrap">
                        <xsl:value-of select="amount-with-nds"/>
                      </fo:block>
                    </fo:table-cell>
<!--                  </xsl:when>-->
<!--                  <xsl:otherwise>-->
<!--                    <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">-->
<!--                      <fo:block end-indent="2pt" font-family="Arial"-->
<!--                        font-size="8.00pt" line-height="8.00pt"-->
<!--                        space-after="1.00pt" space-before="1.00pt"-->
<!--                        text-align="end" wrap-option="no-wrap">-->
<!--                        <xsl:value-of select="nds-usd"/>-->
<!--                      </fo:block>-->
<!--                    </fo:table-cell>-->
<!--                    <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">-->
<!--                      <fo:block end-indent="2pt" font-family="Arial"-->
<!--                        font-size="8.00pt" line-height="8.00pt"-->
<!--                        space-after="1.00pt" space-before="1.00pt"-->
<!--                        text-align="end" wrap-option="no-wrap">-->
<!--                        <xsl:value-of select="amount-with-nds-usd"/>-->
<!--                      </fo:block>-->
<!--                    </fo:table-cell>-->
<!--                  </xsl:otherwise>-->
<!--                </xsl:choose>-->
                <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                  <fo:block font-family="Arial" font-size="8.00pt"
                    line-height="8.00pt" space-after="1.00pt"
                    space-before="1.00pt" text-align="center" wrap-option="no-wrap">----</fo:block>
                </fo:table-cell>
                <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                  <fo:block font-family="Arial" font-size="8.00pt"
                    line-height="8.00pt" space-after="1.00pt"
                    space-before="1.00pt" text-align="center" wrap-option="no-wrap">----</fo:block>
                </fo:table-cell>
              </fo:table-row>
               </xsl:for-each>
              <fo:table-row keep-with-next.within-column="always">
                <fo:table-cell border-color="black" border-style="solid"
                  border-width="0.5pt" number-columns-spanned="7">
                  <fo:block font-family="Arial" font-size="10.00pt"
                    font-weight="bold" line-height="10.00pt"
                    space-after="1.00pt" space-before="1.00pt"
                    start-indent="2.0pt" text-align="start"
                    wrap-option="no-wrap">Всего к оплате</fo:block>
                </fo:table-cell>
<!--                <xsl:choose>-->
<!--                  <xsl:when test="/form/use-rur">-->
                    <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                      <fo:block end-indent="2.0pt" font-family="Arial"
                        font-size="8.00pt" line-height="8.00pt"
                        space-after="2.00pt" space-before="2.00pt"
                        text-align="end" wrap-option="no-wrap"><xsl:value-of select="/form/total/nds"/></fo:block>
                    </fo:table-cell>
                    <fo:table-cell border-color="black" border-style="solid" border-width="0.5pt">
                      <fo:block end-indent="2.0pt" font-family="Arial"
                        font-size="8.00pt" line-height="8.00pt"
                        space-after="2.00pt" space-before="2.00pt"
                        text-align="end" wrap-option="no-wrap"><xsl:value-of select="/form/total/amount-with-nds"/></fo:block>
                    </fo:table-cell>
                <fo:table-cell number-columns-spanned="2">
                  <fo:block/>
                </fo:table-cell>
              </fo:table-row>

              <fo:table-row keep-together.within-column="always">
                <fo:table-cell number-columns-spanned="5">
		<fo:block
                    space-before="-12.00pt"
                    line-height="20.00pt"
                    text-align="start"
                    start-indent="3.70cm"
                    wrap-option="no-wrap">
		<xsl:if test="/form/stamp/stampfacturamanager/graphix/graph">
                  <fo:instream-foreign-object xmlns:svg="http://www.w3.org/2000/svg">
                        <xsl:apply-templates select="/form/stamp/stampfacturamanager/graphix/graph"/>
                  </fo:instream-foreign-object>
		</xsl:if>
                  </fo:block>
                  <fo:block font-family="Arial" font-size="8.00pt"
                    line-height="8.00pt" space-after="3.00pt"
                    space-before="{$space_before_manager}" text-align="start"
                    wrap-option="no-wrap">Руководитель организации ______________________ /<xsl:value-of select="/form/base-manager-name"/>/</fo:block>
                 <fo:block font-family="Arial" font-size="8.00pt" text-align="start" start-indent="7.2cm">   
		                        					  <xsl:choose>
                           							 <xsl:when test="/form/manager-contact/grounds = ''"></xsl:when>
                         						 	<xsl:otherwise>
                         						 		<xsl:value-of select="/form/manager-contact/grounds"/>
                          							</xsl:otherwise>   
                            						 </xsl:choose> 
                  </fo:block>
                  <fo:block start-indent="4.7cm" font-family="Arial" font-size="8.00pt"
                    line-height="8.00pt" space-after="10.00pt"
                    space-before="3.00pt" text-align="start"
                    wrap-option="no-wrap">(подпись)&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;(ф.и.о.)</fo:block>
                  <fo:block end-indent="1.3cm" font-family="Arial"
                    font-size="10.00pt" line-height="10.00pt"
                    space-after="1.00pt" space-before="10.00pt"
                    text-align="end" wrap-option="no-wrap"></fo:block>
                  <fo:block font-family="Arial" font-size="8.00pt"
                    line-height="8.00pt" space-after="1.00pt"
                    space-before="10.00pt" text-align="start" wrap-option="no-wrap"></fo:block>
                  <fo:block font-family="Arial" font-size="7.00pt"
                    line-height="7.00pt" space-after="1.00pt"
                    space-before="10.00pt" text-align="start"
                    wrap-option="no-wrap">ПРИМЕЧАНИЯ: 1. Первый экземпляр - покупателю, второй экземпляр - продавцу.</fo:block>
                  <fo:block font-family="Arial" font-size="7.00pt"
                    line-height="7.00pt" space-after="1.00pt"
                    start-indent="1.86cm" text-align="start"
                    wrap-option="no-wrap"></fo:block>
<!--                  <xsl:if test="/form/use-usd">-->
<!--                    <fo:block font-family="Arial" font-size="7.00pt"-->
<!--                      line-height="7.00pt" space-after="1.00pt"-->
<!--                      start-indent="1.86cm" text-align="start"-->
<!--                      wrap-option="no-wrap">3. 1 у.е. соответствует 1 доллару США по курсу ЦБ РФ, установленному на день оплаты.</fo:block>-->
<!--                  </xsl:if>-->
                </fo:table-cell>
                <fo:table-cell number-columns-spanned="2">
                  <fo:block/>
                </fo:table-cell>
                <fo:table-cell number-columns-spanned="4">
		<fo:block
                    space-before="-12.00pt"
                    line-height="20.00pt"
                    text-align="start"
                    start-indent="3.30cm"
                    wrap-option="no-wrap">
		<xsl:if test="/form/stamp/stampfacturaaccountant/graphix/graph">
                        <fo:instream-foreign-object xmlns:svg="http://www.w3.org/2000/svg">
                                <xsl:apply-templates select="/form/stamp/stampfacturaaccountant/graphix/graph"/>
                        </fo:instream-foreign-object>
		</xsl:if>
                  </fo:block>
                  <fo:block font-family="Arial" font-size="8.00pt"
                    line-height="8.00pt" space-after="3.00pt"
                    space-before="{$space_before_accountant}" text-align="start"
                    wrap-option="no-wrap">Главный бухгалтер ______________________ /<xsl:value-of select="/form/base-acc-name"/>/</fo:block>
                  <fo:block font-family="Arial" font-size="8.00pt" text-align="start" start-indent="6.1cm">   
		                        					  <xsl:choose>
                           							 <xsl:when test="/form/accountant-contact/grounds = ''"></xsl:when>
                         						 	<xsl:otherwise>
                         						 		<xsl:value-of select="/form/accountant-contact/grounds"/>
                          							</xsl:otherwise>   
                            						 </xsl:choose> 
                  </fo:block>
                  <fo:block start-indent="3.7cm" font-family="Arial" font-size="8.00pt"
                    line-height="8.00pt" space-before="3.00pt"
                    text-align="start" wrap-option="no-wrap">(подпись)&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;(ф.и.о.)</fo:block>
                  <fo:block font-family="Arial" font-size="8.00pt"
                    line-height="8.00pt" space-after="1.00pt"
                    text-align="start" wrap-option="no-wrap"></fo:block>
                  <fo:block font-family="Arial" font-size="8.00pt"
                    line-height="8.00pt" space-after="1.00pt"
                    space-before="30pt" text-align="start" wrap-option="no-wrap"></fo:block>
                  <fo:block font-family="Arial" font-size="7.00pt"
                    line-height="7.00pt" space-after="1.00pt"
                    start-indent="1cm" text-align="start"
                    wrap-option="no-wrap"></fo:block>
                </fo:table-cell>
              </fo:table-row>
            </fo:table-body>
          </fo:table>
          <fo:block id="last-page"/>
        </fo:flow>
      </fo:page-sequence>
    </fo:root>
<!--    </xsl:for-each-->
  </xsl:template>

<xsl:template match="graph">
        <xsl:element namespace="http://www.w3.org/2000/svg" name="{@name}">
                <xsl:apply-templates select="param" />
                <xsl:for-each select="graph">
                        <xsl:apply-templates select="." />
                </xsl:for-each>
         </xsl:element>
</xsl:template>

<xsl:template match="param">
        <xsl:attribute name="{@name}"><xsl:value-of select="."/></xsl:attribute>
</xsl:template>
</xsl:stylesheet>

