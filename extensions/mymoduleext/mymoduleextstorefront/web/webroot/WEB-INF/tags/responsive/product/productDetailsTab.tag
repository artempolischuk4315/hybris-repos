<%@ tag body-content="empty" trimDirectiveWhitespaces="true"%>
<%@ attribute name="product" required="true" type="de.hybris.platform.commercefacades.product.data.ProductData"%>
<%@ taglib prefix="ycommerce" uri="http://hybris.com/tld/ycommercetags"%>

<div class="tab-details">
	<ycommerce:testId code="productDetails_content_label">
		<p>
			${ycommerce:sanitizeHTML(product.description)}
		<p>
	</ycommerce:testId>
	<c:if test="${not empty product.additionalInformation}">
                               <div>
                                    <p>
                                      <span style = "font-weight: bold">Additional info: </span>
                                      ${product.additionalInformation}
                                  </p>
                                </div>
      </c:if>
</div>