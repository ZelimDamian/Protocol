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
        "products/:id":    "productDetails",
        "newProduct":		"newProduct",
        "*path":			"home"
    },

    initialize: function () {
    	_.bindAll(this);
        directory.shellView = new directory.ShellView();
        $('body').html(directory.shellView.render().el);
    	this.searchResults = new directory.ProductCollection();
    	this.searchResults.fetch();

        this.$content = $("#content");
    },

    home: function () {

    	directory.homeView = new directory.HomeView({searchResults : this.searchResults});

    	directory.homeView.render();

    	Backbone.history.navigate('', {trigger: true});
    	
        directory.shellView.selectMenuItem('merchant-menu');
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
    directory.loadTemplates(["HomeView", "ContactView", "ShellView", "ProductListView", "ProductView", "ProductSummaryView", "ProductListItemView"],
        function () {
            directory.router = new directory.Router();
            Backbone.history.start();
        });
});
