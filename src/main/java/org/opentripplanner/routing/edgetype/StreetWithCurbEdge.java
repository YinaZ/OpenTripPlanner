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

package org.opentripplanner.routing.edgetype;

import com.vividsolutions.jts.geom.LineString;
import org.opentripplanner.common.geometry.CompactElevationProfile;
import org.opentripplanner.common.geometry.PackedCoordinateSequence;
import org.opentripplanner.routing.graph.Vertex;
import org.opentripplanner.routing.util.ElevationUtils;
import org.opentripplanner.routing.util.SlopeCosts;
import org.opentripplanner.routing.vertextype.StreetVertex;
import org.opentripplanner.util.I18NString;
import org.opentripplanner.util.NonLocalizedString;

/**
 * A StreetEdge with curb data.
 * 
 * @author michaely1113
 */
public class StreetWithCurbEdge extends StreetEdge {

    private static final long serialVersionUID = 1L;

    private float slopeSpeedFactor = 1.0f;

    private float slopeWorkFactor = 1.0f;

    private static final float FACTOR_FLUSH = 0.0f;

    private static final float FACTOR_ROAD_HUMP = 0.5f;

    private static final float FACTOR_LOWERED = 0.0f;

    public StreetWithCurbEdge(StreetVertex v1, StreetVertex v2, LineString geometry,
                              I18NString name, double length, StreetTraversalPermission permission, boolean back) {
        super(v1, v2, geometry, name, length, permission, back);
    }

    @Override
    public StreetWithCurbEdge clone() {
        return (StreetWithCurbEdge) super.clone();
    }

    public boolean setCurbProfile(Vertex v1, String curbData1, Vertex v2, String curbData2) {
        if (v1 == null || v2 == null) {
            return false;
        }
        if (super.isSlopeOverride()) {
            return false;
        }
        boolean slopeLimit = getPermission().allows(StreetTraversalPermission.PEDESTRIAN);

        // TODO make sure this changes cost function
        if (curbData1 == null) {
            curbData1 = "raised";
        }
        if (curbData2 == null) {
            curbData2 = "raised";
        }

        if (curbData1.equals("flush")) {
            slopeWorkFactor += FACTOR_FLUSH;
        }
        if (curbData2.equals("flush")) {
            slopeWorkFactor += FACTOR_FLUSH;
        }

        if (curbData1.equals("lowered")) {
            slopeWorkFactor += FACTOR_LOWERED;
        }
        if (curbData2.equals("lowered")) {
            slopeWorkFactor += FACTOR_LOWERED;
        }

        if (curbData1.equals("flush_[road_hump]")) {
            slopeWorkFactor += FACTOR_ROAD_HUMP;
        }
        if (curbData2.equals("flush_[road_hump]")) {
            slopeWorkFactor += FACTOR_ROAD_HUMP;
        }

        return true;
    }

    @Override
    public double getSlopeSpeedEffectiveLength() {
        return slopeSpeedFactor * getDistance();
    }

    @Override
    public double getSlopeWorkCostEffectiveLength() {
        return slopeWorkFactor * getDistance();
    }

    @Override
    public String toString() {
        return "StreetWithCurbEdge(" + getId() + ", " + getName() + ", " + fromv + " -> "
                + tov + " length=" + this.getDistance() + " carSpeed=" + this.getCarSpeed()
                + " permission=" + this.getPermission() + ")";
    }
}
