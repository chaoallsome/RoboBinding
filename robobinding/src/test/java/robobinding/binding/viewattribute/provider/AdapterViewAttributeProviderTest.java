/**
 * Copyright 2011 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package robobinding.binding.viewattribute.provider;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import robobinding.binding.BindingAttribute;
import robobinding.binding.viewattribute.AdaptedDataSetAttributes;
import robobinding.binding.viewattribute.OnItemClickAttribute;
import robobinding.internal.com_google_common.collect.Maps;
import android.widget.ListView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class AdapterViewAttributeProviderTest
{
	private AdapterViewAttributeProvider listViewAttributeProvider = new AdapterViewAttributeProvider();
	private ListView listView = null;
	private Map<String, String> pendingBindingAttributes;
	
	@Before
	public void setUp()
	{
		listViewAttributeProvider = new AdapterViewAttributeProvider();
		pendingBindingAttributes = Maps.newHashMap();
	}
	
	@Test
	public void givenOnItemClick_ThenCreateAnOnItemClickAttribute()
	{
		pendingBindingAttributes.put("onItemClick", "commandName");
		
		BindingAttribute bindingAttribute = listViewAttributeProvider.createSupportedBindingAttributes(listView, pendingBindingAttributes, false).get(0);
	
		assertThat(bindingAttribute.getViewAttribute(), instanceOf(OnItemClickAttribute.class));
	}
	
	@Test
	public void givenBothSourceAndItemLayout_ThenCreateACompoundAttribute()
	{
		pendingBindingAttributes.put("source", "{sourceProperty}");
		pendingBindingAttributes.put("itemLayout", "@layout/itemLayout");
		
		BindingAttribute bindingAttribute = listViewAttributeProvider.createSupportedBindingAttributes(listView, pendingBindingAttributes, false).get(0);
	
		assertThat(bindingAttribute.getViewAttribute(), instanceOf(AdaptedDataSetAttributes.class));
	}
	
	@Test (expected=RuntimeException.class)
	public void givenOnlySource_ThenThrowRuntimeException()
	{
		pendingBindingAttributes.put("source", "{sourceProperty}");
		
		listViewAttributeProvider.createSupportedBindingAttributes(listView, pendingBindingAttributes, false);
	}
	
	@Test (expected=RuntimeException.class)
	public void givenOnlyItemLayout_ThenThrowRuntimeException()
	{
		pendingBindingAttributes.put("itemLayout", "@layout/itemLayout");
		
		listViewAttributeProvider.createSupportedBindingAttributes(listView, pendingBindingAttributes, false);
	}
}