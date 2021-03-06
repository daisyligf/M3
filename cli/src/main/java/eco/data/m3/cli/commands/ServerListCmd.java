/*
 * Copyright (C) 2018 Blockchain Data Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eco.data.m3.cli.commands;

import eco.data.m3.cli.utils.PropertyPrinter;
import eco.data.m3.routing.api.core.NodeInfo;
import eco.data.m3.routing.api.core.ServerInfo;
import eco.data.m3.routing.api.rest.RestClient;
import eco.data.m3.routing.api.rest.request.ListNodesService;
import eco.data.m3.routing.api.rest.request.ListServerService;
import eco.data.m3.routing.api.rest.response.ListNodesResponse;
import eco.data.m3.routing.api.rest.response.ListServerResponse;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
* @author: xquan
* List Servers Command. 
* Currently only get a server list configuration file on the index server.
* @since: 2018-6-30
**/
@Command(name = "slist", description="List available servers.")
public class ServerListCmd implements Runnable {

	@Option(names = {"-h", "--help"}, usageHelp = true, description = "display this help message")
	boolean usageHelpRequested;

	@Override
	public void run() {
		if(usageHelpRequested)
		{
			CommandLine.usage(this, System.out);
			return;
		}
		
		RestClient client = new RestClient();
		ListServerService req = new ListServerService();
		ListServerResponse resp = (ListServerResponse) client.doRequest(req);
		
		if(resp!=null)
		{
			int index = 0;
			for (ServerInfo server : resp.getServers()) {
				System.out.println(">>> Server "+ index++);
				System.out.println(PropertyPrinter.print(server));
			}
			System.out.println("Total "+ resp.getServers().size() + " Servers.");
		}
	}

}
