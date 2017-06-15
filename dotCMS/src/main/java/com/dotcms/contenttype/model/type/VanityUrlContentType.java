package com.dotcms.contenttype.model.type;

import java.util.List;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

import com.dotcms.contenttype.model.field.DataTypes;
import com.dotcms.contenttype.model.field.Field;
import com.dotcms.contenttype.model.field.ImmutableCustomField;
import com.dotcms.contenttype.model.field.ImmutableSelectField;
import com.dotcms.contenttype.model.field.ImmutableTextField;
import com.dotcms.repackage.com.google.common.collect.ImmutableList;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Provides the basic outline of a Vanity URL Content Type in dotCMS. Vanity
 * URLs are alternate reference paths to internal or external URL's. Vanity URLs
 * are most commonly used to give visitors to the website a more user-friendly
 * or memorable way of reaching an HTML page or File, that might actually live
 * “buried” in a much deeper path.
 * 
 * @author Jose Castro
 * @version 4.2.0
 * @since May 25, 2017
 *
 */
@JsonSerialize(as = ImmutableVanityUrlContentType.class)
@JsonDeserialize(as = ImmutableVanityUrlContentType.class)
@Gson.TypeAdapters
@Value.Immutable
public abstract class VanityUrlContentType extends ContentType {

	private static final long serialVersionUID = 1L;

	private final String TITLE_FIELD_NAME = "Title";
	public static final String TITLE_FIELD_VAR = "title";
	private final String SITE_FIELD_NAME = "Site";
	public static final String SITE_FIELD_VAR = "site";
	private final String URI_FIELD_NAME = "URI";
	public static final String URI_FIELD_VAR = "uri";
	private final String FORWARD_TO_FIELD_NAME = "Forward To";
	public static final String FORWARD_TO_FIELD_VAR = "forwardTo";
	private final String ACTION_FIELD_NAME = "Action";
	public static final String ACTION_FIELD_VAR = "action";
	private final String ORDER_FIELD_NAME = "Order";
	public static final String ORDER_FIELD_VAR = "order";

	public abstract static class Builder implements ContentTypeBuilder {
	}

	@Override
	public BaseContentType baseType() {
		return BaseContentType.VANITY_URL;
	}

	@Override
	public List<Field> requiredFields() {
		int order = 1;
		Field titleField = ImmutableTextField.builder().name(TITLE_FIELD_NAME).variable(TITLE_FIELD_VAR)
				.dataType(DataTypes.TEXT).required(Boolean.TRUE).listed(Boolean.TRUE).indexed(Boolean.TRUE)
				.sortOrder(order).fixed(Boolean.TRUE).searchable(Boolean.TRUE).build();
		Field siteField = ImmutableCustomField.builder().name(SITE_FIELD_NAME).variable(SITE_FIELD_VAR)
				.dataType(DataTypes.TEXT).fixed(Boolean.TRUE).indexed(Boolean.TRUE)
				.values("$velutil.mergeTemplate('/static/content/site_selector_field_render.vtl')")
				.required(Boolean.TRUE).sortOrder(order++).listed(Boolean.TRUE).build();
		Field uriField = ImmutableTextField.builder().name(URI_FIELD_NAME).variable(URI_FIELD_VAR)
				.dataType(DataTypes.TEXT).indexed(Boolean.TRUE).searchable(Boolean.TRUE).required(Boolean.TRUE)
				.sortOrder(order++).fixed(Boolean.TRUE).listed(Boolean.TRUE).build();
		Field forwardToField = ImmutableCustomField.builder().name(FORWARD_TO_FIELD_NAME).variable(FORWARD_TO_FIELD_VAR)
				.dataType(DataTypes.TEXT).fixed(Boolean.TRUE).indexed(Boolean.TRUE)
				.values("$velutil.mergeTemplate('/static/content/file_browser_field_render.vtl')")
				.required(Boolean.TRUE).sortOrder(order++).listed(Boolean.TRUE).build();
		Field actionField = ImmutableSelectField.builder().name(ACTION_FIELD_NAME).variable(ACTION_FIELD_VAR)
				.required(Boolean.TRUE).fixed(Boolean.TRUE).indexed(Boolean.TRUE).searchable(Boolean.TRUE)
				.dataType(DataTypes.INTEGER).values("Forward|200\r\nPermanent Redirect|301\r\nTemporary Redirect|307\r\nAuth Required|401\r\nAuth Failed|403\r\nMissing|404\r\nError|500").build();
		Field orderField = ImmutableTextField.builder().name(ORDER_FIELD_NAME).variable(ORDER_FIELD_VAR)
				.dataType(DataTypes.INTEGER).required(Boolean.TRUE).fixed(Boolean.TRUE).indexed(Boolean.TRUE)
				.searchable(Boolean.TRUE).build();
		return ImmutableList.of(titleField, siteField, uriField, forwardToField, actionField, orderField);
	}

}
