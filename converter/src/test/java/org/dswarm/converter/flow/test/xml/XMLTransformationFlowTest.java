/**
 * Copyright (C) 2013 – 2017 SLUB Dresden & Avantgarde Labs GmbH (<code@dswarm.org>)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dswarm.converter.flow.test.xml;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.dswarm.converter.flow.JSONTransformationFlow;
import org.dswarm.converter.flow.JSONTransformationFlowFactory;
import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import org.dswarm.converter.DMPConverterException;
import org.dswarm.converter.GuicedTest;
import org.dswarm.converter.flow.utils.DMPConverterUtils;
import org.dswarm.persistence.model.job.Component;
import org.dswarm.persistence.model.job.Job;
import org.dswarm.persistence.model.job.Mapping;
import org.dswarm.persistence.model.job.Task;
import org.dswarm.persistence.model.job.Transformation;
import org.dswarm.persistence.util.DMPPersistenceUtil;

/**
 * @author tgaengler Created by tgaengler on 16/05/14.
 */
public class XMLTransformationFlowTest extends GuicedTest {

	@Test
	public void testMabxmlComplexMapping() throws Exception {

		testXMLTaskWithTuples("mabxml_dmp.complex-mapping.result.json", "mabxml_dmp.complex-mapping.task.json", "mabxml_dmp.tuples.json");
	}

	@Test
	public void testMabxmlWFilterMapping() throws Exception {

		testXMLTaskWithTuples("mabxml_w_filter.task.result.json", "mabxml_w_filter.task.json", "test-mabxml.tuples.json");
	}

	@Test
	public void testMabxmlWFilterMapping2() throws Exception {

		testXMLTaskWithTuples("mabxml_w_filter_2.task.result.json", "mabxml_w_filter_2.task.json", "mabxml_dmp.tuples.json");
	}

	@Test
	public void testMabxmlWFilterMappings() throws Exception {

		testXMLTaskWithTuples("tgtest_mabxml_mo_proj.task.result.json", "tgtest_mabxml_mo_proj.task.json", "test-mabxml.tuples.json");
	}

	@Test
	public void testMabxmlOneMappingWithFilterAndMultipleFunctions() throws Exception {

		testXMLTaskWithTuples("dd-528.mabxml.task.result.json", "dd-528.mabxml.task.json", "mabxml_dmp.tuples.json");
	}

	/**
	 * filter (on top key) + collect
	 *
	 * @throws Exception
	 */
	@Test
	public void testOaipmhMarcxmlOneMappingWithFilterToSelectMultipleValues() throws Exception {

		testXMLTaskWithTuples("filter.task.result.json", "filter.task.json", "oai-pmh_marcxml.tuples.json");
	}

	/**
	 * filter (on top key + regexed subkey) + collect
	 *
	 * @throws Exception
	 */
	@Test
	public void testOaipmhMarcxmlOneMappingWithFilterToSelectMultipleValuesPlusCollect() throws Exception {

		testXMLTaskWithTuples("filter_collect.task.result.json", "filter_collect.task.json", "oai-pmh_marcxml.tuples.json");
	}

	@Test
	public void testMabxmlConcatOneMappingOnFeldValueWithTwoFiltersMorph() throws Exception {

		testXMLMorphWithTuples("dd-530.mabxml.morph.result.json", "dd-530.mabxml.morph.xml", "test-mabxml.tuples.json");
	}

	@Test
	public void testMabxmlConcatOneMappingOnFeldValueWithTwoFiltersTask() throws Exception {

		testXMLTaskWithTuples("dd-530.mabxml.task.result.json", "dd-530.mabxml.task.json", "test-mabxml.tuples.json");
	}

	@Test
	public void testMabxmlConcatOneMappingOnFeldValueWithTwoFiltersTask2() throws Exception {

		testXMLTaskWithTuples("dd-530.mabxml.task.result.1.json", "dd-530.mabxml.task.1.json", "test-mabxml.tuples.json");
	}

	@Test
	public void testMabxmlFilterWithRegexMorph() throws Exception {

		testXMLMorphWithTuples("dd-650.mabxml.morph.result.json", "dd-650.mabxml.morph.xml", "test-mabxml.tuples.json");
	}

	@Test
	public void testMabxmlFilterWithRegexTask() throws Exception {

		testXMLTaskWithTuples("dd-650.mabxml.task.result.json", "dd-650.mabxml.task.json", "test-mabxml.tuples.json");
	}

	@Test
	public void testDd727Morph() throws Exception {

		testXMLMorphWithTuples("dd-727.mabxml.morph.result.json", "dd-727.mabxml.morph.xml", "dd-727.mabxml.tuples.json");
	}

	@Test
	public void testDd727Task() throws Exception {

		testXMLTaskWithTuples("dd-727.mabxml.task.result.json", "dd-727.mabxml.task.json", "dd-727.mabxml.tuples.json");
	}

	@Test
	public void testDeweyTask() throws Exception {

		testXMLTaskWithTuples("dewey.mabxml.task.result.json", "dewey.mabxml.task.json", "dewey.mabxml_dmp.tuples.json");
	}

	@Test
	public void testConvertValueTask() throws Exception {

		testXMLTaskWithTuples("convertvalue.mabxml.task.result.json", "convertvalue.task.json", "convertvalue.mabxml_dmp.tuples.json");
	}

	@Test
	public void testISSNTask() throws Exception {

		testXMLTaskWithTuples("issn.mabxml.task.result.json", "issn.task.json", "issn.mabxml_dmp.tuples.json");
	}

	@Test
	public void testSipHashTask() throws Exception {

		testXMLTaskWithTuples("siphash.mabxml.task.result.json", "siphash.task.json", "issn.mabxml_dmp.tuples.json");
	}

	@Test
	public void testBase64Task() throws Exception {

		testXMLTaskWithTuples("base64.mabxml.task.result.json", "base64.task.json", "issn.mabxml_dmp.tuples.json");
	}

	@Test
	public void testCollectTask() throws Exception {

		testXMLTaskWithTuples("dd-1220.mabxml.task.result.json", "dd-1220.mabxml.task.json", "test-mabxml.tuples.json");
	}

	@Test
	public void testHTTPAPIRequestTask() throws Exception {

		testXMLTaskWithTuples("dd-72.mabxml.task.result.json", "dd-72.mabxml.task.json", "dd-72.test-mabxml.tuples.json");
	}

	@Test
	public void testParseJSONTask() throws Exception {

		testXMLTaskWithTuples("dd-970.mabxml.task.result.json", "dd-970.mabxml.task.json", "dd-970.test-mabxml.tuples.json");
	}

	@Test
	public void testDd734Morph() throws Exception {

		testXMLMorphWithTuples("sub.entity.3level.mabxml.morph.result.json", "sub.entity.3level.mabxml.morph.xml", "mabxml.tuples.json");
	}

	@Test
	public void testDd905Morph() throws Exception {

		testXMLMorphWithTuples("dd-905.morph.result.json", "dd-905.morph.xml", "mabxml.tuples.json");
	}

	@Test
	public void testDd906Task() throws Exception {

		testXMLTaskWithTuples("dd-906.lookups.task.result.json", "dd-906.lookups.task.json", "mabxml.tuples.json");
	}

	@Test
	public void testDd907Morph() throws Exception {

		testXMLMorphWithTuples("dd-907.collectors.morph.result.json", "dd-907.collectors.morph.xml", "mabxml.tuples.json");
	}

	@Test
	public void testDd907Task() throws Exception {

		testXMLTaskWithTuples("dd-907.collectors.task.result.json", "dd-907.collectors.task.json", "test-mabxml.tuples.json");
	}

	/**
	 * should emit the values of the if-branch
	 *
	 * @throws Exception
	 */
	@Test
	public void testIfElseTask() throws Exception {

		testXMLTaskWithTuples("if-else.collectors.task.result.json", "if-else.collectors.task.json", "test-mabxml.tuples.json");
	}

	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void testNumFilterTask() throws Exception {

		testXMLTaskWithTuples("numfilter.function.task.result.json", "numfilter.function.task.json", "test-mabxml.tuples.json");
	}

	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void testEqualsFilterTask() throws Exception {

		testXMLTaskWithTuples("equals.filter.function.task.result.json", "equals.filter.function.task.json", "test-mabxml.tuples.json");
	}

	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void testNotEqualsFilterTask() throws Exception {

		testXMLTaskWithTuples("not-equals.filter.function.task.result.json", "not-equals.filter.function.task.json", "test-mabxml.tuples.json");
	}

	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void testFincRVKBeanShellScriptTask() throws Exception {

		testXMLTaskWithTuples("dd-1298.task.result.json", "dd-1298.task.json", "fincmarc_small2.tuples.json");
	}

	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void testFincMiscDel152BeanShellScriptTask() throws Exception {

		testXMLTaskWithTuples("misc_del152.task.result.2.json", "misc_del152.task.plain.json", "fincmarc_small2.tuples.json");
	}

	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void testFincMiscDel152BeanShellScriptTask2() throws Exception {

		testXMLMorphWithTuples("misc_del152.task.result.json", "misc_del152.task.morph.xml", "fincmarc_small2.tuples.json");
	}

	/**
	 * should select two values that are explicitly filtered
	 *
	 * @throws Exception
	 */
	@Test
	public void testFilterEncodingMappingTask() throws Exception {

		testXMLMorphWithTuples("neuer_test.task.result.json", "neuer_test.task.morph.xml", "mabxml_dmp.tuples.json");
	}

	/**
	 * should select two values that are explicitly filtered
	 *
	 * @throws Exception
	 */
	@Test
	public void testFilterEncodingMappingTask2() throws Exception {

		testXMLTaskWithTuples("neuer_test.task.result.json", "neuer_test.task.json", "mabxml_dmp.tuples.json");
	}


	@Test
	public void testSqlMapTask() throws Exception {

		final Task task = getTask("sqlmap.lookup.task.json");
		final Job job = task.getJob();
		final Set<Mapping> mappings = job.getMappings();
		final Mapping mapping = mappings.iterator().next();
		final Component mappingTransformationComponent = mapping.getTransformation();
		final Transformation mappingTransformationComponentFunction = (Transformation) mappingTransformationComponent.getFunction();
		final Set<Component> mappingTransformationComponentFunctionComponents = mappingTransformationComponentFunction.getComponents();
		final Component sqlMapLookup = mappingTransformationComponentFunctionComponents.iterator().next();
		final Map<String, String> sqlMapLookupParameterMappings = sqlMapLookup.getParameterMappings();

		final String user = readManuallyFromTypeSafeConfig("dswarm.db.metadata.username");
		final String pass = readManuallyFromTypeSafeConfig("dswarm.db.metadata.password");
		final String db = readManuallyFromTypeSafeConfig("dswarm.db.metadata.schema");

		setSqlMapParameterMappings(sqlMapLookupParameterMappings, user, pass, db);

		testXMLTaskWithTuples("sqlmap.lookup.task.result.json", "sqlmap.mabxml.tuples.json", task);
	}

	@Test
	public void testSqlDbRequestTask() throws Exception {

		final Task task = getTask("sqldbrequest.task.json");
		final Job job = task.getJob();
		final Set<Mapping> mappings = job.getMappings();
		final Mapping mapping = mappings.iterator().next();
		final Component mappingTransformationComponent = mapping.getTransformation();
		final Transformation mappingTransformationComponentFunction = (Transformation) mappingTransformationComponent.getFunction();
		final Set<Component> mappingTransformationComponentFunctionComponents = mappingTransformationComponentFunction.getComponents();
		final Component sqlMapLookup = mappingTransformationComponentFunctionComponents.iterator().next();
		final Map<String, String> sqlMapLookupParameterMappings = sqlMapLookup.getParameterMappings();

		final String user = readManuallyFromTypeSafeConfig("dswarm.db.metadata.username");
		final String pass = readManuallyFromTypeSafeConfig("dswarm.db.metadata.password");
		final String db = readManuallyFromTypeSafeConfig("dswarm.db.metadata.schema");

		setSqlMapParameterMappings(sqlMapLookupParameterMappings, user, pass, db);

		testXMLTaskWithTuples("sqldbrequest.task.result.json", "sqldbrequest.mabxml.tuples.json", task);
	}

	@Test
	public void testSqlMapSTask() throws Exception {

		final Task task = getTask("dd-1386.task.json");
		final Job job = task.getJob();
		final Set<Mapping> mappings = job.getMappings();
		final Mapping mapping = mappings.iterator().next();
		final Component mappingTransformationComponent = mapping.getTransformation();
		final Transformation mappingTransformationComponentFunction = (Transformation) mappingTransformationComponent.getFunction();
		final Set<Component> mappingTransformationComponentFunctionComponents = mappingTransformationComponentFunction.getComponents();
		final Iterator<Component> iterator = mappingTransformationComponentFunctionComponents.iterator();

		final String user = readManuallyFromTypeSafeConfig("dswarm.db.metadata.username");
		final String pass = readManuallyFromTypeSafeConfig("dswarm.db.metadata.password");
		final String db = readManuallyFromTypeSafeConfig("dswarm.db.metadata.schema");

		while(iterator.hasNext()) {

			final Component component = iterator.next();

			if("sqlmap".equals(component.getFunction().getName())) {

				final Map<String, String> sqlMapLookupParameterMappings = component.getParameterMappings();

				setSqlMapParameterMappings(sqlMapLookupParameterMappings, user, pass, db);
			}
		}

		testXMLTaskWithTuples("dd-1386.task.result.json", "dd-1386.marcxml.tuples.json", task);
	}

	/**
	 * should emit the values of the else-branch
	 *
	 * @throws Exception
	 */
	@Test
	public void testIfElseTask2() throws Exception {

		testXMLTaskWithTuples("if-else.collectors.task.result.2.json", "if-else.collectors.task.json", "if-else.test-mabxml.tuples.json");
	}

	@Test
	public void testDd980Task() throws Exception {

		testXMLTaskWithTuples("dd-980.xml.task.result.json", "dd-980.xml.task.json", "rvk_lokal_cdata.tuples.json");
	}

	@Test
	public void testDd980Morph() throws Exception {

		testXMLMorphWithTuples("dd-980.xml.morph.result.json", "dd-980.morph.xml", "rvk_lokal_cdata.tuples.json");
	}

	@Test
	public void testMetsmodsXmlWithFilterAndMapping() throws Exception {

		testXMLTaskWithTuples("metsmods_small.xml.task.result.json", "metsmods_small.xml.task.json", "metsmods_small.xml.tuples.json");
	}

	@Test
	public void testXMLWithFilterCommonAttributePathOnRoot() throws Exception {

		testXMLTaskWithTuples("dd-651.xml.task.result.json", "dd-651.xml.task.json", "testset5.xml.tuples.json");
	}

	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void testOptionalConcatValuesFromMultipleFieldsTask() throws Exception {

		testXMLMorphWithTuples("base.allfields.task.result.json", "base.allfields.task.morph.xml", "base_dc_testdaten.tuples.json");
	}

	@Test
	public void testDD1399Morph() throws Exception {

		testXMLMorphWithTuples("dd-1399/dd-1399.morph.result.json", "dd-1399/dd-1399.task.morph.xml", "dd-1399/dd-1399.input.tuples.json");
	}

	@Test
	public void testDD1406Morph() throws Exception {

		testXMLMorphWithTuples("dd-1406/dd-1406.morph.result.json", "dd-1406/dd-1406.task.morph.xml", "dd-1406/dd-1406.input.tuples.json");
	}

	@Test
	public void testDD1409Morph() throws Exception {

		testXMLMorphWithTuples("dd-1409/dd-1409.morph.result.json", "dd-1409/dd-1409.task.morph.xml", "dd-1409/dd-1409.input.tuples.json");
	}

	@Test
	public void testDD1410Morph() throws Exception {

		testXMLMorphWithTuples("dd-1410/dd-1410.morph.result.json", "dd-1410/dd-1410.task.morph.xml", "dd-1409/dd-1409.input.tuples.json");
	}

	@Test
	public void testDD1397Morph() throws Exception {

		testXMLMorphWithTuples("dd-1397/dd-1397.morph.result.json", "dd-1397/dd-1397.task.morph.xml", "dd-1397/dd-1397.input.tuples.json");
	}

	@Test
	public void testDD1399Task() throws Exception {

		testXMLTaskWithTuples("dd-1399/dd-1399.result.json", "dd-1399/dd-1399.task.json", "dd-1399/dd-1399.input.tuples.json");
	}

	@Test
	public void testDD1406Task() throws Exception {

		testXMLTaskWithTuples("dd-1406/dd-1406.result.json", "dd-1406/dd-1406.task.json", "dd-1406/dd-1406.input.tuples.json");
	}

	@Test
	public void testDD1409Task() throws Exception {

		testXMLTaskWithTuples("dd-1409/dd-1409.result.json", "dd-1409/dd-1409.task.json", "dd-1409/dd-1409.input.tuples.json");
	}

	@Test
	public void testDD1410Task() throws Exception {

		testXMLTaskWithTuples("dd-1410/dd-1410.result.json", "dd-1410/dd-1410.task.json", "dd-1409/dd-1409.input.tuples.json");
	}

	@Test
	public void testDD1397Task() throws Exception {

		testXMLTaskWithTuples("dd-1397/dd-1397.result.json", "dd-1397/dd-1397.task.json", "dd-1397/dd-1397.input.tuples.json");
	}


	/**
	 *
	 * @throws Exception
	 */
	@Test
	public void testOptionalConcatValuesFromMultipleFieldsTask2() throws Exception {

		testXMLTaskWithTuples("base.allfields.task.result.2.json", "base.allfields.task.json", "base_dc_testdaten.tuples.json");
	}

	private void testXMLTaskWithTuples(final String taskResultJSONFileName, final String taskJSONFileName, final String tuplesJSONFileName)
			throws Exception {

		final Task task = getTask(taskJSONFileName);

		testXMLTaskWithTuples(taskResultJSONFileName, tuplesJSONFileName, task);

	}

	private Task getTask(final String taskJSONFileName) throws IOException {

		final String finalTaskJSONString = DMPPersistenceUtil.getResourceAsString(taskJSONFileName);
		final ObjectMapper objectMapper = DMPPersistenceUtil.getJSONObjectMapper();

		return objectMapper.readValue(finalTaskJSONString, Task.class);
	}

	private void testXMLTaskWithTuples(final String taskResultJSONFileName, final String tuplesJSONFileName, final Task task)
			throws IOException, DMPConverterException, JSONException {

		// looks like that the utilised ObjectMappers getting a bit mixed, i.e., actual sometimes delivers a result that is not in
		// pretty print and sometimes it is in pretty print ... (that's why the reformatting of both - expected and actual)
		final ObjectMapper objectMapper2 = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL).configure(
				SerializationFeature.INDENT_OUTPUT, true);

		final String expected = DMPPersistenceUtil.getResourceAsString(taskResultJSONFileName);

		final JSONTransformationFlowFactory flowFactory = GuicedTest.injector
				.getInstance(JSONTransformationFlowFactory.class);

		final JSONTransformationFlow flow = flowFactory.fromTask(task);

		flow.getScript();

		final String actual = flow.applyResource(tuplesJSONFileName).toBlocking().first();
		final ArrayNode array = objectMapper2.readValue(actual, ArrayNode.class);

		final ArrayNode expectedArray = objectMapper2.readValue(expected, ArrayNode.class);

		final JsonNode acutalJSONNode = DMPConverterUtils.removeRecordIdFields(array);
		final JsonNode expectedJSONNode = DMPConverterUtils.removeRecordIdFields(expectedArray);

		final String finalActual = objectMapper2.writeValueAsString(acutalJSONNode);
		final String finalExpected = objectMapper2.writeValueAsString(expectedJSONNode);

		JSONAssert.assertEquals(finalExpected, finalActual, true);
	}

	private void testXMLMorphWithTuples(final String resultJSONFileName, final String morphXMLFileName, final String tuplesJSONFileName)
			throws Exception {

		final String expected = DMPPersistenceUtil.getResourceAsString(resultJSONFileName);

		final JSONTransformationFlowFactory flowFactory = GuicedTest.injector
				.getInstance(JSONTransformationFlowFactory.class);

		final String finalMorphXmlString = DMPPersistenceUtil.getResourceAsString(morphXMLFileName);

		// looks like that the utilised ObjectMappers getting a bit mixed, i.e., actual sometimes delivers a result that is not in
		// pretty print and sometimes it is in pretty print ... (that's why the reformatting of both - expected and actual)
		final ObjectMapper objectMapper2 = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL).configure(
				SerializationFeature.INDENT_OUTPUT, true);

		// final Task task = objectMapper.readValue(finalTaskJSONString, Task.class);

		final JSONTransformationFlow flow = flowFactory.fromString(finalMorphXmlString);

		flow.getScript();

		final String actual = flow.applyResource(tuplesJSONFileName).toBlocking().first();
		final ArrayNode array = objectMapper2.readValue(actual, ArrayNode.class);

		final ArrayNode expectedArray = objectMapper2.readValue(expected, ArrayNode.class);

		final JsonNode acutalJSONNode = DMPConverterUtils.removeRecordIdFields(array);
		final JsonNode expectedJSONNode = DMPConverterUtils.removeRecordIdFields(expectedArray);

		final String finalActual = objectMapper2.writeValueAsString(acutalJSONNode);
		final String finalExpected = objectMapper2.writeValueAsString(expectedJSONNode);

		JSONAssert.assertEquals(finalExpected, finalActual, true);
	}

	private void setSqlMapParameterMappings(final Map<String, String> sqlMapLookupParameterMappings, final String user, final String pass, final String db) {

		sqlMapLookupParameterMappings.put("login", user);
		sqlMapLookupParameterMappings.put("password", pass);
		sqlMapLookupParameterMappings.put("database", db);
	}
}
