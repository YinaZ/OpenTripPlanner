/* This program is free software: you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public License
 as published by the Free Software Foundation, either version 3 of
 the License, or (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>. */

package org.opentripplanner.graph_builder.module.osm;

import org.opentripplanner.extra_graph.Curb;
import org.opentripplanner.graph_builder.services.GraphBuilderModule;
import org.opentripplanner.routing.graph.Graph;
import org.opentripplanner.routing.graph.Vertex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Adds curb data from Open Street Map into the graph
 */
public class CurbModule implements GraphBuilderModule {

    /** An set of ids which identifies what stages this graph builder provides (i.e. streets, elevation, transit) */
    public List<String> provides() { return Arrays.asList("curb"); }

    /** A list of ids of stages which must be provided before this stage */
    public List<String> getPrerequisites() {
        return Arrays.asList("streets");
    }

    private static final Logger LOG = LoggerFactory.getLogger(CurbModule.class);

    @Override
    public void buildGraph(Graph graph, HashMap<Class<?>, Object> extra) {
        LOG.info("Setting curb data from OSM..");
        @SuppressWarnings("unchecked")
        HashMap<Vertex, String> extraElevation = (HashMap<Vertex, String>) extra.get(Curb.class);

        int nTotal = 0;
        int nProcessed = 0;
        for (Vertex gv : graph.getVertices()) {
            LOG.info(extraElevation.toString());
            if (extraElevation.containsKey(gv)) {
                // TODO add curb data to the vertex, also only 7000 vertices in graph... needs all
                nTotal++;
            }
        }

        LOG.info("Processed " + nProcessed + " vertices out of " + nTotal + " total vertices...");
        System.exit(1);
    }

    @Override
    public void checkInputs() {
        // no inputs to check
    }
}