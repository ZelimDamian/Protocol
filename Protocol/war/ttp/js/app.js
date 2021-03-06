var directory = {

    views: {},

    models: {},

    loadTemplates: function(views, callback) {

        var deferreds = [];

        $.each(views, function(index, view) {
            if (directory[view]) {
                deferreds.push($.get('tpl/' + view + '.html', function(data) {
                    directory[view].prototype.template = _.template(data);
                }, 'html'));
            } else {
                alert(view + " not found");
            }
        });

        $.when.apply(null, deferreds).done(callback);
    }

};

directory.Router = Backbone.Router.extend({

    routes: {
        "":                 "home",
        "contact":          "contact",
        "products/:id":    	"productDetails",
        "initiate/:id":		"initiate",
        "newProduct":		"newProduct",
        "*path":			"home"
    },

    initialize: function () {
    	_.bindAll(this);
        directory.shellView = new directory.ShellView();
        $('body').html(directory.shellView.render().el);

        this.$content = $("#content");
    },

    home: function () {

    	directory.homeView = new directory.HomeView({});

    	directory.homeView.render();

    	//Backbone.history.navigate('', {trigger: true});
    	
        directory.shellView.selectMenuItem('ttp-menu');
    },

    initiate: function(id){
    	directory.homeView = new directory.HomeView({});

    	directory.homeView.trustedPartyView.productId = id;
    	directory.homeView.render();
    },
    
    contact: function () {
        if (!directory.contactView) {
            directory.contactView = new directory.ContactView();
            directory.contactView.render();
        }
        this.$content.html(directory.contactView.el);
        
        directory.shellView.selectMenuItem('contact-menu');
    },

    productDetails: function (id) {
    	if(directory.homeView == null)
    		this.home();
    	else
    		directory.homeView.renderProduct(id);
    },
    
    newProduct: function () {
    	if(directory.homeView == null)
    		this.home();
    	else
    		directory.homeView.newProduct();
    }

});

$(document).on("ready", function () {
    directory.loadTemplates(["HomeView",
                             "KeyExchangeView",
                             "TrustedPartyView",
                             "ContactView",
                             "ShellView",
             				 "ProductListView",
             				 "ProductView",
                             "ProductSummaryView",
                             "ProductListItemView"],
        function () {
            directory.router = new directory.Router();
            Backbone.history.start();
        });
});
