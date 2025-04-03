package com.echevarne.sap.cloud.facturacion.odata.annotations.enums;

public enum SemanticsEnum {
	
	NONE(null), // None
	TEL("tel"),  //Telephone number
	TEL_CELL_WORK("tel;type=cell,work"),  //Work cellphone number; see explanation below table for more values
	TEL_TYPE_FAX("tel;type=fax"),  //Fax number
	EMAIL("email"),  //Email address
	EMAIL_TYPE_PREF("email;type=pref"),  //Preferred email address
	URL("url"),  //Web URL
	NAME("name"),  //Formatted text of the full name
	GIVENNAME("givenname"),  //First name or given name of a person
	MIDDLENAME("middlename"),  //Middle name of a person
	FAMILYNAME("familyname"),  //Last name or family name of a person
	NICKNAME("nickname"),  //Descriptive name given instead of or in addtion to the one marked as "name"
	HONORIFIC("honorific"),  //Title of a person (Ph.D., Dr., ...)
	SUFFIX("suffix"),  //Suffix to the name of a person
	NOTE("note"),  //Supplemental information or a comment that is associated with the vCard
	PHOTO("photo"),  //URL of a photo of a person
	CITY("city"),  //Address: city
	STREET("street"),  //Address: street
	COUNTRY("country"),  //Address: country
	REGION("region"),  //Address: state or province
	ZIP("zip"),  //Address: postal code
	POBOX("pobox"),  //Address: post office box
	ORG("org"),  //Organization name
	ORG_UNIT("org-unit"),  //Organizational unit
	ORG_ROLE("org-role"),  //Organizational role
	TITLE("title"),  //Job title
	BDAY("bday"),  //Birth date
	SUMMARY("summary"),  //Calendar: summary of a calendar component
	DESCRIPTION("description"),  //Calendar: description of a calendar component, detailing the summary
	CATEGORIES("categories"),  //Calendar: comma-separated list of categories for a calendar component
	DTSTART("dtstart"),  //Calendar: the date and time that a calendar component starts
	DTEND("dtend"),  //Calendar: the date and time that a calendar component ends
	DURATION("duration"),  //Calendar: duration as an alternative to dtend, see xs:duration
	DUE("due"),  //Calendar: the date and time that a to-do is expected to be completed
	COMPLETED("completed"),  //Calendar: the date and time that a to-do was actually completed
	PRIORITY("priority"),  //Calendar: the relative priority for a calendar component, 0 for undefined, 1 for highest, ... 9 for lowest
	CLASS("class"),  //Calendar: access classification for a calendar component
	STATUS("status"),  //Calendar: overall status or confirmation for the calendar component
	PERCENT_COMPLETE("percent-complete"),  //Calendar: percent completion of a to-do., ranging from 0 to 100 (integer)
	CONTACT("contact"),  //Calendar: contact information or alternatively a reference to contact information associated with the calendar component
	LOCATION("location"),  //Calendar: the intended venue for the activity defined by a calendar component
	TRANSP("transp"),  //Calendar: defines whether or not an event is transparaent to busy time searches
	FBTYPE("fbtype"),  //Calendar: free/busy time type, see [ iCalendar, Section 3.2.9 ]
	WHOLEDAY("wholeday"),  //Calendar: "true" or "false, depending on whether an event is scheduled for an entire day
	YEAR("year"),  //Calendar: year as string following the regex pattern (-?)YYYY(Y*) consisting of an optional minus sign for years B.C. followed by at least four digits
	YEARMONTH("yearmonth"),  //Calendar: year and month as string following the regex pattern (-?)YYYY(Y*)MM consisting of an optional minus sign for years B.C. followed by at least six digits, the last two digits are a number between 01 and 12 representing the months January to December
	YEARMONTHDAY("yearmonthday"),  //Calendar: year, month and day as string following the logical pattern (-?)YYYY(Y*)MMDD consisting of an optional minus sign for years B.C. followed by at least eight digits, where the last four digits represent the months January to December (MM) and the day of the month (DD). The string matches the regex pattern -?([1-9][0-9]{3,}|0[0-9]{3})(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01]) The regex pattern does not reflect the additional constraint for "Day-of-month Values": The day value must be no more than 30 if month is one of 04, 06, 09, or 11, no more than 28 if month is 02 and year is not divisible by 4, or is divisible by 100 but not by 400, and no more than 29 if month is 02 and year is divisible by 400, or by 4 but not by 100
	FROM("from"),  //Mail: author of message, see [ RFC5322, section 3.6.2 ]
	SENDER("sender"),  //Mail: mailbox of agent responsible for actual transmission
	TO("to"),  //Mai: comma-separated list of primary recipients, see [ RFC5322, section 3.6.3 ]
	CC("cc"),  //Mail: carbon copy, comma-separated
	BCC("bcc"),  //Mail: blind carbon copy, comma-separated
	SUBJECT("subject"),  //Mail: topic of the message
	BODY("body"),  //Mail: message body
	KEYWORDS("keywords"),  //Mail: comma-separated list of important words and phrases that might be useful for the recipient
	RECEIVED("received"),  //Mail: DateTime the message was received
	GEO_LON("geo-lon"),  //Geolocation: longitude
	GEO_LAT("geo-lat"),  //Geolocation: latitude
	CURRENCY_CODE("currency-code"),  //Currency code, preferably ISO
	UNIT_OF_MEASURE("unit-of-measure"),  //Unit of measure, preferably ISO
	COUNT("count");  //Aggregation: the number of unaggregated entities that have been aggregated into the response entity (count(*) in SQL). Only valid for one property of an entity type that is annotated with sap:semantics="aggregate".
	
	public final String semantic;
	 
    private SemanticsEnum(String semantic) {
        this.semantic = semantic;
    }
    
    public String toString() {
        return this.semantic;
    }
}