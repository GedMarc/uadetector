package net.sf.uadetector;

import net.sf.uadetector.ReadableDeviceCategory.Category;
import net.sf.uadetector.exception.IllegalEmptyArgumentException;
import net.sf.uadetector.exception.IllegalNullArgumentException;
import org.junit.Test;

import static org.fest.assertions.Assertions.*;

public final class DeviceCategoryTest
{

	@Test
	public void blueprintEqualsBuilder()
	{
		DeviceCategory blueprint = new Blueprint().category(Category.SMARTPHONE)
		                                          .icon("icn")
		                                          .infoUrl("url")
		                                          .name("a name")
		                                          .build();
		DeviceCategory.Builder builder = new DeviceCategory.Builder();

		builder.setCategory(Category.SMARTPHONE);
		builder.setIcon("icn");
		builder.setInfoUrl("url");
		builder.setName("a name");

		DeviceCategory obj = builder.build();
		assertThat(obj).isEqualTo(blueprint);
	}

	@Test
	public void equals_different_CATEGORY()
	{
		DeviceCategory a = new Blueprint().category(Category.OTHER)
		                                  .build();
		DeviceCategory b = new Blueprint().category(Category.PERSONAL_COMPUTER)
		                                  .build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_ICON()
	{
		DeviceCategory a = new Blueprint().icon("icn1")
		                                  .build();
		DeviceCategory b = new Blueprint().icon("icn2")
		                                  .build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_INFOURL()
	{
		DeviceCategory a = new Blueprint().infoUrl("info1")
		                                  .build();
		DeviceCategory b = new Blueprint().infoUrl("info2")
		                                  .build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_different_NAME()
	{
		DeviceCategory a = new Blueprint().name("name1")
		                                  .build();
		DeviceCategory b = new Blueprint().name("name2")
		                                  .build();
		assertThat(a.equals(b)).isFalse();
		assertThat(a.hashCode() == b.hashCode()).isFalse();
	}

	@Test
	public void equals_identical()
	{
		DeviceCategory a = new Blueprint().build();
		DeviceCategory b = new Blueprint().build();
		assertThat(b).isEqualTo(a);
		assertThat(a.hashCode() == b.hashCode()).isTrue();
	}

	@Test
	public void equals_null()
	{
		DeviceCategory a = new Blueprint().build();
		assertThat(a.equals(null)).isFalse();
	}

	@Test
	public void equals_otherClass()
	{
		DeviceCategory a = new Blueprint().build();
		assertThat(a.equals("")).isFalse();
	}

	@Test
	public void equals_same()
	{
		DeviceCategory a = new Blueprint().build();
		assertThat(a).isEqualTo(a);
		assertThat(a).isSameAs(a);
		assertThat(a.hashCode() == a.hashCode()).isTrue();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_CATEGORY()
	{
		new Blueprint().category(null)
		               .build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_ICON()
	{
		new Blueprint().icon(null)
		               .build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_INFOURL()
	{
		new Blueprint().infoUrl(null)
		               .build();
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void precondition_NAME_notEmpty()
	{
		new Blueprint().name("")
		               .build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_NAME_notNull()
	{
		new Blueprint().name(null)
		               .build();
	}

	private static final class Blueprint
	{

		private Category category = Category.SMART_TV;

		private String icon = "icon";

		private String infoUrl = "info-url";

		private String name = "name";

		public Blueprint()
		{
			// default constructor
		}

		@javax.validation.constraints.NotNull
		public DeviceCategory build()
		{
			return new DeviceCategory(category, icon, infoUrl, name);
		}

		public Blueprint category(Category category)
		{
			this.category = category;
			return this;
		}

		public Blueprint icon(String icon)
		{
			this.icon = icon;
			return this;
		}

		public Blueprint infoUrl(String infoUrl)
		{
			this.infoUrl = infoUrl;
			return this;
		}

		public Blueprint name(String name)
		{
			this.name = name;
			return this;
		}

	}

}
