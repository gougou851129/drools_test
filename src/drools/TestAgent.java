package drools;

import java.util.ArrayList;
import java.util.List;

import org.drools.KnowledgeBase;
import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentConfiguration;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.io.ResourceChangeScannerConfiguration;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;

public class TestAgent {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ResourceChangeScannerConfiguration sconf = ResourceFactory
				.getResourceChangeScannerService()
				.newResourceChangeScannerConfiguration();
		// ���ö�ʱɨ��ʱ������Ĭ��60s
		sconf.setProperty("drools.resource.scanner.interval", "30");
		ResourceFactory.getResourceChangeScannerService().configure(sconf);
		//����ɨ��ͼ�������
		ResourceFactory.getResourceChangeScannerService().start();
		ResourceFactory.getResourceChangeNotifierService().start();
		//create a agent cinfig
		KnowledgeAgentConfiguration aconf = KnowledgeAgentFactory.newKnowledgeAgentConfiguration();
		//�Ƿ�ɨ��Ŀ¼
		aconf.setProperty("drools.agent.scanDirectories","true");  
        aconf.setProperty("drools.agent.newInstance","true"); 
        //create a agent
        KnowledgeAgent kagent = KnowledgeAgentFactory.newKnowledgeAgent("testname",aconf);  
        kagent.applyChangeSet(ResourceFactory.newClassPathResource("resources/resource.xml"));
        //no use
       // KnowledgeBuilder kbd = KnowledgeBuilderFactory.newKnowledgeBuilder();
        
        //create a stateful session
        KnowledgeBase kbase = kagent.getKnowledgeBase();
        //no use
        //kbase.addKnowledgePackages(kbd.getKnowledgePackages());
        StatefulKnowledgeSession statefulSession=kbase.newStatefulKnowledgeSession();
        //�������÷���ʾʹ�����һ�α����knowledgebase��session
       //StatelessKnowledgeSession statelessSession= kagent.newStatelessKnowledgeSession();
        Person p = new Person();
		p.setAge(30);
		p.setSex("F");
		p.setName("Mike");
		statefulSession.insert(p);
//		statelessSession.execute(p);
		List<String> lst = new ArrayList<String>();
		statefulSession.setGlobal("list",lst );
		statefulSession.fireAllRules();
		for(String str: lst){
			System.out.println(str);
		}
		
//		statefulSession.dispose();
        
	}

}
