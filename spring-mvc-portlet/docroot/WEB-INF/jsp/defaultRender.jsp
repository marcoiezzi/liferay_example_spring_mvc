<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<portlet:defineObjects />


<div id="pieChart" style="text-align: center"></div>

<script src="//cdnjs.cloudflare.com/ajax/libs/d3/3.4.4/d3.min.js"></script>

<script>

var utenti=null;

AUI().use('liferay-portlet-url', 'aui-base', 'aui-io', function( A ) {
  
    
    var resourceURL = Liferay.PortletURL.createResourceURL();
    resourceURL.setPortletId( "springmvc_WAR_springmvcportlet" );
    resourceURL.setResourceId("utenteResource");
    resourceURL.setCopyCurrentRenderParameters(true);
     console.log(resourceURL.toString());
    A.io.request( resourceURL.toString(), {
        dataType: 'json',
        on: {
            success: function(event, id, obj) {
                utenti = this.get('responseData');
                console.log("utenti="+utenti);
                    drawPie();
                
            }
        }
    });
});


function drawPie(){
var pie = new d3pie("pieChart", {
	"header": {
		"title": {
			"text": "User Age distribution",
			"fontSize": 24,
			"font": "open sans"
		},
		"subtitle": {
			"text": "A full pie chart to show off label collision detection and resolution.",
			"color": "#999999",
			"fontSize": 12,
			"font": "open sans"
		},
		"titleSubtitlePadding": 9
	},
	"footer": {
		"color": "#999999",
		"fontSize": 10,
		"font": "open sans",
		"location": "bottom-left"
	},
	"size": {
		"canvasWidth": 590
	},
	"data": {
		"sortOrder": "value-desc",
		"content": utenti.utenti
	},
	"labels": {
		"outer": {
			"pieDistance": 32
		},
		"inner": {
			"hideWhenLessThanPercentage": 3
		},
		"mainLabel": {
			"fontSize": 11
		},
		"percentage": {
			"color": "#ffffff",
			"decimalPlaces": 0
		},
		"value": {
			"color": "#adadad",
			"fontSize": 11
		},
		"lines": {
			"enabled": true
		}
	},
	"effects": {
		"pullOutSegmentOnClick": {
			"effect": "linear",
			"speed": 400,
			"size": 8
		}
	},
	"misc": {
		"gradient": {
			"enabled": true,
			"percentage": 100
		}
	}
});

}
</script>


